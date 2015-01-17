package uk.co.addhop.mapeditor;

import com.apple.eawt.AppEvent;
import com.apple.eawt.Application;
import com.apple.eawt.QuitResponse;
import uk.co.addhop.mapeditor.map.MapView;
import uk.co.addhop.mapeditor.map.MapViewController;
import uk.co.addhop.mapeditor.menubar.MainMenuBar;
import uk.co.addhop.mapeditor.menubar.MainMenuBarController;
import uk.co.addhop.mapeditor.models.Brush;
import uk.co.addhop.mapeditor.models.Map;
import uk.co.addhop.mapeditor.models.TileTypeDatabase;
import uk.co.addhop.mapeditor.palette.NewPaletteView;
import uk.co.addhop.mapeditor.palette.PaletteViewController;
import uk.co.addhop.mapeditor.toolbar.ToolbarController;
import uk.co.addhop.mapeditor.toolbar.ToolbarModel;
import uk.co.addhop.mapeditor.toolbar.ToolbarView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class MainApplication implements com.apple.eawt.QuitHandler {

    public static final int MAX_OPENED_WINDOWS = 10;
    public static final String PREFS_LAST_OPENED_MAP = "LAST_OPENED_MAP_";

    private Preferences preferences;
    public static String documentPath;
    private TileTypeDatabase tileTypeDatabase;

    private MainMenuBar mainMenuBar;
    private java.util.List<MapWindow> mapWindowList = new ArrayList<MapWindow>();
    private MapWindow focused;
    private PopupMenu recentMenu;
    private MainMenuBarController mainMenuBarController;

    public void init() {

        // set the look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MapEditor v0.1");

        // take the menu bar off the jframe
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        documentPath = System.getProperty("user.home") + "/Library/" + "MapEditor";

        final boolean success = (new File(documentPath)).mkdirs();
        if (!success) {
            System.out.println("Document directory failed to create: " + documentPath);
        }

        preferences = Preferences.userNodeForPackage(this.getClass());

        mainMenuBarController = new MainMenuBarController();
        mainMenuBarController.setModel(this);

        mainMenuBar = new MainMenuBar();
        mainMenuBar.setController(mainMenuBarController);

        final JMenuBar menuBar = mainMenuBar.getView();
        final PopupMenu popupMenu = createPopupMenu();

        // Uses application to set the dock menu and main menu
        // This is Apple specific
        final Application application = Application.getApplication();

        if (application != null) {
            System.out.println("Application exists");

            application.setDefaultMenuBar(menuBar);
            application.setDockMenu(popupMenu);
            application.setQuitHandler(this);

            updateRecentMenus();

            tileTypeDatabase = new TileTypeDatabase();
            tileTypeDatabase.loadDatabase();

            loadPreviouslyOpenedMaps();
        }
    }

    private void loadPreviouslyOpenedMaps() {
        // Load any previously open map windows
        for (int i = 0; i < MAX_OPENED_WINDOWS; i++) {
            final String recentMap = preferences.get(PREFS_LAST_OPENED_MAP + i, "empty");
            if (!"empty".equals(recentMap)) {
                final Map map = new Map(recentMap);
                createMapWindow(map);
            }
        }
    }

    public JFrame createMapWindow(final Map mapModel) {


        final Brush brush = new Brush();

        // Set up main map panel
        final MapViewController mapViewController = new MapViewController();
        mapViewController.setBrush(brush);
        mapViewController.setModel(mapModel);

        final MapView mapView = new MapView();
        mapView.setDatabase(tileTypeDatabase);
        mapView.setController(mapViewController);

        mapModel.setDatabase(tileTypeDatabase);
        mapModel.addObserver(mapView);

        mapModel.notifyObservers(mapModel);

        // Set up palette panel
        final PaletteViewController paletteViewController = new PaletteViewController();
        final NewPaletteView paletteView = new NewPaletteView();
        paletteViewController.setModel(brush);
        paletteView.setDatabase(tileTypeDatabase);
        paletteView.setController(paletteViewController);

        tileTypeDatabase.addObserver(paletteView);

        // Set up toolbar
        final ToolbarModel toolbarModel = new ToolbarModel();
        final ToolbarView toolbarView = new ToolbarView();
        final ToolbarController toolbarController = new ToolbarController(toolbarModel, mapModel);

        toolbarModel.addObserver(toolbarView);
        toolbarView.makeToolbar(toolbarController);


        final MapWindow appWindow = new MapWindow(mapModel, brush);

        mapWindowList.add(appWindow);

        // Set up split
        appWindow.getContentPane().setLayout(new BorderLayout());

        final JScrollPane westScroll = new JScrollPane(paletteView, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        final JScrollPane centerScroll = new JScrollPane(mapView, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        appWindow.getContentPane().add(toolbarView, BorderLayout.PAGE_START);
        appWindow.getContentPane().add(westScroll, BorderLayout.WEST);
        appWindow.getContentPane().add(centerScroll, BorderLayout.CENTER);
        appWindow.getContentPane().add(new JPanel(), BorderLayout.SOUTH); // Space filler
//        appWindow.pack();

        appWindow.setSize(1024, 800);
        appWindow.setVisible(true);

        appWindow.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                System.out.println("windowOpened - " + appWindow.getTitle());
            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("windowClosing - " + appWindow.getTitle());

                final String filename = mapModel.getFileName();
                if (filename == null) {
                    final JDialog dialog = new JDialog(appWindow, "Save before quitting?", true);

                    final JPanel panel = new JPanel(new BorderLayout());
                    final JLabel label = new JLabel("Do you wish to save " + mapModel.getMapName() + " map before quitting?");
                    label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    panel.add(label, BorderLayout.CENTER);

                    final JPanel buttonPanel = new JPanel();
                    final JButton okButton = (JButton) buttonPanel.add(new JButton("OK"));
                    final JButton cancelButton = (JButton) buttonPanel.add(new JButton("Cancel"));
                    panel.add(buttonPanel, BorderLayout.PAGE_END);

                    dialog.getContentPane().add(panel);

                    okButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            final JFileChooser chooser = new JFileChooser();
                            final int returnVal = chooser.showSaveDialog(null);

                            if (returnVal == JFileChooser.APPROVE_OPTION) {
                                //System.out.println("You chose to open this file: " +
                                final String filePath = chooser.getSelectedFile().getAbsolutePath();
                                mapModel.saveTileSet(filePath);

                                // Add to most recent file list
                            }

                            dialog.dispose();
                        }
                    });

                    cancelButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dialog.dispose();
                        }
                    });

                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                } else {
                    mapModel.saveTileSet(mapModel.getFileName());
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("windowClosed - " + appWindow.getTitle());

                mapWindowList.remove(appWindow);

                if (focused == appWindow) {
                    focused = null;
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
                System.out.println("windowIconified - " + appWindow.getTitle());
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                System.out.println("windowDeiconified - " + appWindow.getTitle());
            }

            @Override
            public void windowActivated(WindowEvent e) {
                System.out.println("windowActivated - " + appWindow.getTitle());

                focused = appWindow;
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                System.out.println("windowDeactivated - " + appWindow.getTitle());
            }
        });

        return appWindow;
    }

    private PopupMenu createPopupMenu() {
        recentMenu = new PopupMenu("Recent maps");

        updateRecentMenus();

        // Dock popup menu requires AWT classes
        final PopupMenu popupMenu = new PopupMenu("Map Editor");
        popupMenu.add(recentMenu);
        return popupMenu;
    }

    private void updateRecentMenus() {
        // TODO Load app preferences to do with recently open maps
        for (int i = 0; i < 5; i++) {
            final String recentMap = preferences.get("RECENT_MAP_" + i, "empty");
            if (!"empty".equals(recentMap)) {
                recentMenu.add(new MenuItem(recentMap));
            }
        }
    }

    public MapWindow getFocusedWindow() {
        return focused;
    }

    public List<MapWindow> getMapWindowList() {
        return mapWindowList;
    }

    @Override
    public void handleQuitRequestWith(AppEvent.QuitEvent quitEvent, QuitResponse quitResponse) {
        System.out.println("handleQuitRequestWith " + quitEvent.toString());

        // Save all to recent list
        for (int i = 0; i < mapWindowList.size(); i++) {
            final MapWindow window = mapWindowList.get(i);
            final Map map = window.getMap();
            if (map.getFileName() != null) {
                preferences.put(PREFS_LAST_OPENED_MAP + i, map.getFileName());
            }
        }

        quitResponse.performQuit();
    }

    public static class MapWindow extends JFrame {
        private final Map map;
        private final Brush brush;

        public MapWindow(final Map map, final Brush brush) {
            super();

            setTitle(map.getMapName() + " - " + map.getFileName());

            this.map = map;
            this.brush = brush;
        }

        public Map getMap() {
            return map;
        }

        public Brush getBrush() {
            return brush;
        }
    }
}
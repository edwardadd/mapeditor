package uk.co.addhop.mapeditor;

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
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.prefs.Preferences;

public class MainApplication extends JFrame {

    private static final int MAX_OPENED_WINDOWS = 10;
    private static final String PREFS_LAST_OPENED_MAP = "LAST_OPENED_MAP_";
    private static final String PREFS_RECENT_MAP = "RECENT_MAP_";
    private static final int MAX_RECENTLIST_SIZE = 5;

    private Preferences preferences;
    private TileTypeDatabase tileTypeDatabase;

    private MainMenuBar mainMenuBar;
    private java.util.List<MapWindow> mapWindowList = new ArrayList<MapWindow>();
    private MapWindow focused;
    private PopupMenu recentMenu;

    private Deque<String> recentList = new ArrayDeque<String>();

    public void init() {

        // set the look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setSystemProperties();

        final boolean success = (new File(getDocumentPath())).mkdirs();
        if (!success) {
            System.out.println("Document directory failed to create: " + getDocumentPath());
        }

        preferences = Preferences.userNodeForPackage(this.getClass());

        updateRecentListFromPrefs();

        final MainMenuBarController mainMenuBarController = new MainMenuBarController();
        mainMenuBarController.setModel(this);

        mainMenuBar = new MainMenuBar();
        mainMenuBar.setController(mainMenuBarController);

        final JMenuBar menuBar = mainMenuBar.getView();
//        final PopupMenu popupMenu = createPopupMenu();

        this.setJMenuBar(menuBar);
//        this.setMinimumSize(new Dimension(300, 300));

        this.pack();
        this.setVisible(true);
//        this.setExtendedState(this.MAXIMIZED_BOTH);
//
//            application.setQuitStrategy(QuitStrategy.SYSTEM_EXIT_0);

        //updateRecentMenus();

            tileTypeDatabase = new TileTypeDatabase();
            tileTypeDatabase.loadDatabase();

            loadPreviouslyOpenedMaps();
    }

    public void setSystemProperties() {
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MapEditor v0.1");

        // take the menu bar off the jframe
        System.setProperty("apple.laf.useScreenMenuBar", "true");
    }

    public static String getDocumentPath() {
        return System.getProperty("user.home") + "/Library/" + "MapEditor";
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
        final ToolbarModel toolbarModel = new ToolbarModel(brush);
        final ToolbarView toolbarView = new ToolbarView();
        final ToolbarController toolbarController = new ToolbarController();

        toolbarModel.addObserver(toolbarView);
        toolbarView.makeToolbar(toolbarController);
        toolbarController.setModel(toolbarModel);

        // Create window
        final MapWindow appWindow = new MapWindow(mapModel, brush);

        // Set up menu bar
        final MainMenuBar menuBar = new MainMenuBar();
        final MainMenuBarController menuBarController = new MainMenuBarController();
        menuBarController.setModel(MainApplication.this);
        menuBarController.setParent(appWindow);
        menuBar.setController(menuBarController);

        appWindow.setJMenuBar(menuBar.getView());
        mapWindowList.add(appWindow);

        updateWindowMenus();

        appWindow.setup(mapView, toolbarView, paletteView);

        return appWindow;
    }

    private void updateWindowMenus() {
        final JMenu menu = MainMenuBar.getWindowMenu();
        menu.removeAll();

        for (MapWindow window : mapWindowList) {
            menu.add(new JMenuItem(window.getTitle()));
        }
    }

    private PopupMenu createPopupMenu() {
        recentMenu = new PopupMenu("Recent maps");

        updateRecentMenus();

        // Dock popup menu requires AWT classes
        final PopupMenu popupMenu = new PopupMenu("Map Editor");
        popupMenu.add(recentMenu);
        return popupMenu;
    }

    private void updateRecentListFromPrefs() {
        for (int i = 0; i < MAX_RECENTLIST_SIZE; i++) {
            final String recentMap = preferences.get(PREFS_RECENT_MAP + i, "empty");
            if (!"empty".equals(recentMap)) {
                recentList.push(recentMap);
            }
        }
    }

    private void saveRecentListToPrefs() {
        for (int i = 0; i < MAX_RECENTLIST_SIZE; i++) {
            preferences.put(PREFS_RECENT_MAP + i, "empty");
        }

        int i = 0;
        for (String recentMap : recentList) {
            preferences.put(PREFS_RECENT_MAP + i, recentMap);
            i++;
        }
    }

    private void updateRecentMenus() {
        recentMenu.removeAll();

        for (final String recentMap : recentList) {
            final MenuItem item = recentMenu.add(new MenuItem(recentMap));
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final Map map = new Map(recentMap);
                    createMapWindow(map);
                }
            });
        }

        final JMenu recentMainMenu = MainMenuBar.getFileOpenRecentMenu();
        recentMainMenu.removeAll();

        for (final String recentMap : recentList) {
            final JMenuItem item = recentMainMenu.add(new JMenuItem(recentMap));
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final Map map = new Map(recentMap);
                    createMapWindow(map);
                }
            });
        }
    }

    public boolean isMapLoaded(final String filename) {
        for (final MapWindow window : mapWindowList) {
            if (window.getMap().getFileName().equals(filename)) {
                return true;
            }
        }

        return false;
    }

    public MapWindow getFocusedWindow() {
        return focused;
    }

    public void setFocusedWindow(MapWindow newFocus) {
        focused = newFocus;
    }

    public void clearFocusedWindow() {
        setFocusedWindow(null);
    }

    public List<MapWindow> getMapWindowList() {
        return mapWindowList;
    }

    public void removeWindow(MapWindow window) {
        mapWindowList.remove(window);
    }

//    @Override
//    public void handleQuitRequestWith(AppEvent.QuitEvent quitEvent, QuitResponse quitResponse) {
//        System.out.println("handleQuitRequestWith " + quitEvent.toString());
//
//        // Save all to recent list
//        for (int i = 0; i < MAX_OPENED_WINDOWS; i++) {
//            preferences.put(PREFS_LAST_OPENED_MAP + i, "empty");
//        }
//
//        int i = 0;
//        for (MapWindow window : mapWindowList) {
//            final Map map = window.getMap();
//            if (map.getFileName() != null) {
//                preferences.put(PREFS_LAST_OPENED_MAP + i, map.getFileName());
//                i++;
//            }
//        }
//
////        for (MapWindow window : mapWindowList) {
////            window.dispose();
////        }
//
//        saveRecentListToPrefs();
//
//        try {
//            preferences.flush();
//        } catch (BackingStoreException e) {
//            e.printStackTrace();
//        }
//
//        quitResponse.performQuit();
////        quitResponse.cancelQuit();
////        System.exit(0);
//    }

    public void addToRecentList(String fileName) {
        for (String other : recentList) {
            if (other.equals(fileName)) {
                recentList.remove(other);
            }
        }

        recentList.push(fileName);

        updateRecentMenus();
    }

    public TileTypeDatabase getDatabase() {
        return tileTypeDatabase;
    }

}
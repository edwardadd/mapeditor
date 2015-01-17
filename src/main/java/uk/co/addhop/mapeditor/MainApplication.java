package uk.co.addhop.mapeditor;

import com.apple.eawt.Application;
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
import java.io.File;
import java.util.prefs.Preferences;

public class MainApplication {

    private Preferences preferences;
    public static String documentPath;
    private TileTypeDatabase tileTypeDatabase;

    private MainMenuBar mainMenuBar;

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

        final MainMenuBarController mainMenuBarController = new MainMenuBarController();
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

            updateRecentMenus();

            tileTypeDatabase = new TileTypeDatabase();
            tileTypeDatabase.loadDatabase();

            final Map mapModel = new Map();
            createMapWindow(mapModel);
        }
    }

    private JFrame createMapWindow(final Map mapModel) {

        final JFrame appWindow = new JFrame("Map Editor v0.1");

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

        // Set up palette panel
        final PaletteViewController paletteViewController = new PaletteViewController();
//            final PaletteView paletteView = new PaletteView();
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

        // Set up split
        appWindow.getContentPane().setLayout(new BorderLayout());

        final JScrollPane westScroll = new JScrollPane(paletteView, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        final JScrollPane centerScroll = new JScrollPane(mapView, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        appWindow.getContentPane().add(toolbarView, BorderLayout.PAGE_START);
        appWindow.getContentPane().add(westScroll, BorderLayout.WEST);
        appWindow.getContentPane().add(centerScroll, BorderLayout.CENTER);
        appWindow.getContentPane().add(new JPanel(), BorderLayout.SOUTH);
//        appWindow.pack();

        appWindow.setSize(1024, 800);
        appWindow.setVisible(true);

        return appWindow;
    }

    private PopupMenu createPopupMenu() {
        final PopupMenu recentMenu = new PopupMenu("Recent maps");
        recentMenu.add(new MenuItem("Item 1"));

        // Dock popup menu requires AWT classes
        PopupMenu popupMenu = new PopupMenu("Map Editor");
        popupMenu.add(recentMenu);
        return popupMenu;
    }

    private void updateRecentMenus() {
        // TODO Load app preferences to do with recently open maps
        for (int i = 0; i < 5; i++) {
            String recentMap = preferences.get("RECENT_MAP_" + i, "empty");
            if (!"empty".equals(recentMap)) {

            }
        }
    }
}
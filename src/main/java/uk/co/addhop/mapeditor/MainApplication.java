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
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainApplication extends JFrame implements WindowManagerInterface {

    public static final int MAX_OPENED_WINDOWS = 10;

    private TileTypeDatabase tileTypeDatabase;

    private java.util.List<MapWindow> mapWindowList = new ArrayList<>();
    private MapWindow focused;
    private RecentMapsManager recentMapsManager;

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

        recentMapsManager = new RecentMapsManager();


        final MainMenuBarController mainMenuBarController = new MainMenuBarController(recentMapsManager);
        mainMenuBarController.setModel(this);

        final MainMenuBar mainMenuBar = new MainMenuBar(this, recentMapsManager, true);
        mainMenuBar.setController(mainMenuBarController);

        final JMenuBar menuBar = mainMenuBar.getView();
        this.setJMenuBar(menuBar);
        this.pack();
        this.setVisible(true);

        tileTypeDatabase = new TileTypeDatabase();
        tileTypeDatabase.loadDatabase();

        loadPreviouslyOpenedMaps();


        // TODO On quit remember all currently opened windows/maps
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
        final List<String> recent = recentMapsManager.recentlyOpenedMapsList();

        // Load any previously open map windows
        for (final String recentMap : recent) {
            final Map map = new Map(recentMap);
            final MapWindow window = createMapWindow(map);
            mapWindowList.add(window);
        }
    }

    @Override
    public MapWindow createMapWindow(final Map mapModel) {

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
        final MapWindow appWindow = new MapWindow(mapModel, brush, this);

        // Set up menu bar
        final MainMenuBar menuBar = new MainMenuBar(this, recentMapsManager, false);
        final MainMenuBarController menuBarController = new MainMenuBarController(recentMapsManager);
        menuBarController.setModel(MainApplication.this);
        menuBarController.setParent(appWindow);
        menuBar.setController(menuBarController);

        appWindow.setJMenuBar(menuBar.getView());
        mapWindowList.add(appWindow);

        appWindow.setup(mapView, toolbarView, paletteView);

        menuBar.updateWindowMenu();

        return appWindow;
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

    public TileTypeDatabase getDatabase() {
        return tileTypeDatabase;
    }

}
package uk.co.addhop.mapeditor;

import com.apple.eawt.Application;
import uk.co.addhop.mapeditor.map.MapModel;
import uk.co.addhop.mapeditor.map.MapView;
import uk.co.addhop.mapeditor.map.MapViewController;
import uk.co.addhop.mapeditor.palette.PaletteView;
import uk.co.addhop.mapeditor.palette.PaletteViewController;
import uk.co.addhop.mapeditor.palette.TileTypeDatabase;
import uk.co.addhop.mapeditor.toolbar.ToolbarController;
import uk.co.addhop.mapeditor.toolbar.ToolbarModel;
import uk.co.addhop.mapeditor.toolbar.ToolbarView;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

public class MainApplication {

    private JMenu fileOpenRecentMenu;
    private PopupMenu recentMenu;

    private Preferences preferences;

    public void init() {

        preferences = Preferences.userNodeForPackage(this.getClass());

        fileOpenRecentMenu = new JMenu("Open Recent");

        JMenu menu = new JMenu("File");
        menu.add(new JMenuItem("New..."));
        menu.add(new JMenuItem("Open..."));
        menu.add(fileOpenRecentMenu);
        menu.add(new JMenuItem("Close Map"));
        menu.addSeparator();
        menu.add(new JMenuItem("Settings..."));
        menu.addSeparator();
        menu.add(new JMenuItem("Save All"));

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);

        recentMenu = new PopupMenu("Recent maps");
        recentMenu.add(new MenuItem("Item 1"));

        // Dock popup menu requires AWT classes
        PopupMenu popupMenu = new PopupMenu("Map Editor");
        popupMenu.add(recentMenu);

        // Uses application to set the dock menu and main menu
        // This is Apple specific
        Application application = Application.getApplication();

        if (application != null) {
            System.out.println("Application exists");

            application.setDefaultMenuBar(menuBar);
            application.setDockMenu(popupMenu);
        }

        updateRecentMenus();

        JFrame appWindow = new JFrame("Map Editor v1.0");

        TileTypeDatabase tileTypeDatabase = new TileTypeDatabase();
        tileTypeDatabase.loadDatabase();

        // Set up main map panel
        MapModel mapModel = new MapModel();
        MapViewController mapViewController = new MapViewController();

        MapView mapView = new MapView();
        mapView.initialize(appWindow, mapViewController);

        mapViewController.setViewModel(mapView, mapModel);

        mapModel.setDatabase(tileTypeDatabase);
        mapModel.addObserver(mapView);

        // Set up palette panel
        PaletteViewController paletteViewController = new PaletteViewController();
        PaletteView paletteView = new PaletteView();
        paletteView.setDatabase(tileTypeDatabase);
        paletteView.setController(paletteViewController);

        tileTypeDatabase.addObserver(paletteView);

        // Set up toolbar
        ToolbarModel toolbarModel = new ToolbarModel();
        ToolbarView toolbarView = new ToolbarView();
        ToolbarController toolbarController = new ToolbarController(toolbarModel, mapModel);

        toolbarModel.addObserver(toolbarView);
        toolbarView.makeToolbar(toolbarController);

        // Set up split
        appWindow.getContentPane().setLayout(new BorderLayout());

        JScrollPane westScroll = new JScrollPane(paletteView);
        JScrollPane centerScroll = new JScrollPane(mapView);

        appWindow.getContentPane().add(toolbarView, BorderLayout.PAGE_START);
        appWindow.getContentPane().add(westScroll, BorderLayout.WEST);
        appWindow.getContentPane().add(centerScroll, BorderLayout.CENTER);
//        appWindow.pack();

        appWindow.setSize(1024, 800);
        appWindow.setVisible(true);
    }

    private void updateRecentMenus()
    {
        // TODO Load app preferences to do with recently open maps
        for (int i = 0; i < 5; i++)
        {
            String recentMap = preferences.get("RECENT_MAP_" + i, "empty");
            if (!"empty".equals(recentMap))
            {

            }
        }
    }
}
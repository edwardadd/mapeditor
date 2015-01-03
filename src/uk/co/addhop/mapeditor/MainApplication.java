package uk.co.addhop.mapeditor;

import com.apple.eawt.Application;

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
        appWindow.setSize(600, 400);
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
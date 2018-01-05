package uk.co.addhop.mapeditor.menubar;

import uk.co.addhop.mapeditor.MapWindow;
import uk.co.addhop.mapeditor.RecentMapsManager;
import uk.co.addhop.mapeditor.WindowManagerInterface;
import uk.co.addhop.mapeditor.interfaces.View;
import uk.co.addhop.mapeditor.models.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;

/**
 * MainMenuBar
 * <p/>
 * Created by edwardaddley on 16/01/15.
 */
public class MainMenuBar implements View<JMenuBar, MainMenuBarController>, ActionListener {

    // The window and recent menus will be the same across all menus
    private static JMenu fileOpenRecentMenu;
    private static JMenu windowMenu;

    // macOS we need to identify the current menu bar
    private boolean appMenuBar;

    private JMenuBar menuBar;
    private MainMenuBarController controller;

    private RecentMapsManager recentMapsManager;
    private WindowManagerInterface windows;

    public MainMenuBar(WindowManagerInterface windows, RecentMapsManager recentMapsManager, final boolean appMenuBar) {
        this.windows = windows;
        this.recentMapsManager = recentMapsManager;
        this.appMenuBar = appMenuBar;

        if (fileOpenRecentMenu == null) {
            fileOpenRecentMenu = new JMenu("Open Recent");
            fileOpenRecentMenu.setMnemonic(KeyEvent.VK_R);
        }

        final JMenu menu = new JMenu("File");
        menu.add(createMenuItem("New...", "NEW", KeyEvent.VK_N, KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())));
        menu.add(createMenuItem("New... Cave", "CAVE", KeyEvent.VK_E, KeyStroke.getKeyStroke(KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())));
        menu.add(createMenuItem("Open...", "LOAD", KeyEvent.VK_O, KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())));
        menu.add(createMenuItem("Save", "SAVE", KeyEvent.VK_S, KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())));
        menu.add(createMenuItem("Save As...", "SAVE_AS", KeyEvent.VK_A, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())));
        menu.add(fileOpenRecentMenu);
        menu.add(createMenuItem("Close", "CLOSE", KeyEvent.VK_C, KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())));
        menu.addSeparator();
        menu.add(createMenuItem("Save All", "SAVE_ALL", KeyEvent.VK_L, null));

        final JMenu tileSheetMenu = new JMenu("Tile Library");
        tileSheetMenu.add(createMenuItem("Add...", "ADD_TILE_SHEET", KeyEvent.VK_T, KeyStroke.getKeyStroke(KeyEvent.VK_T, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())));

        if (windowMenu == null) {
            windowMenu = new JMenu("Window");
        }
        menuBar = new JMenuBar();
        menuBar.add(menu);
        menuBar.add(tileSheetMenu);
        menuBar.add(windowMenu);
    }

    private JMenuItem createMenuItem(final String label, final String action, final int m, final KeyStroke keyStroke) {
        final JMenuItem item = new JMenuItem(label);
        item.setActionCommand(action);
        item.setMnemonic(m);
        item.setAccelerator(keyStroke);
        item.addActionListener(this);
        return item;
    }

    public void updateRecentsMenu() {
        final JMenu recentMainMenu = fileOpenRecentMenu;
        recentMainMenu.removeAll();

        for (final String recentMap : recentMapsManager.getRecentList()) {
            final JMenuItem item = recentMainMenu.add(new JMenuItem(recentMap));
            item.addActionListener(event -> {
                final Map map = new Map(recentMap);
                windows.createMapWindow(map);
            });
        }
    }

    public void updateWindowMenu() {
        final JMenu menu = windowMenu;
        menu.removeAll();

        for (final MapWindow window : windows.getMapWindowList()) {
            menu.add(new JMenuItem(window.getTitle()));
            // TODO Switch to window
        }
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        System.out.println("actionPerformed - " + e.getActionCommand());

        switch (e.getActionCommand()) {
            case "NEW":
                controller.newMap();
                break;
            case "CAVE":
                controller.newCaveMap();
                break;
            case "LOAD":
                controller.loadMap();
                break;
            case "SAVE":
                controller.saveMap();
                break;
            case "SAVE_AS":
                controller.saveMapAs();
                break;
            case "SAVE_ALL":
                // TODO SAVE_ALL
                break;
            case "CLOSE":
                controller.closeMap();
                break;
            case "ADD_TILE_SHEET":
                controller.addTileSheet();
                break;
        }
    }

    @Override
    public JMenuBar getView() {
        return menuBar;
    }

    @Override
    public void setController(MainMenuBarController controller) {
        this.controller = controller;
    }

    @Override
    public MainMenuBarController getController() {
        return controller;
    }

    @Override
    public void update(Observable o, Object arg) {
        updateRecentsMenu();
        updateWindowMenu();
    }

    public static JMenu getWindowMenu() {
        return windowMenu;
    }

    public boolean isAppMenuBar() {
        return appMenuBar;
    }

    public void setAppMenuBar(boolean appMenuBar) {
        this.appMenuBar = appMenuBar;
    }
}

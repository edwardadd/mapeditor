package uk.co.addhop.mapeditor.menubar;

import uk.co.addhop.mapeditor.interfaces.Controller;
import uk.co.addhop.mapeditor.interfaces.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

/**
 * MainMenuBar
 * <p/>
 * Created by edwardaddley on 16/01/15.
 */
public class MainMenuBar implements View<JMenuBar, MainMenuBarController>, ActionListener {

    private JMenuBar menuBar;
    private MainMenuBarController controller;

    public MainMenuBar() {
        final JMenu fileOpenRecentMenu = new JMenu("Open Recent");

        final JMenu menu = new JMenu("File");
        menu.add(createMenuItem("New...", "NEW", this));
        menu.add(createMenuItem("Open...", "LOAD", this));
        menu.add(createMenuItem("Save", "SAVE", this));
        menu.add(createMenuItem("Save As...", "SAVE_AS", this));
        menu.add(fileOpenRecentMenu);
        menu.add(createMenuItem("Close", "CLOSE", this));
        menu.addSeparator();
        menu.add(createMenuItem("Save All", "SAVE_ALL", this));

//        menu.addActionListener(this);

        menuBar = new JMenuBar();
        menuBar.add(menu);
    }

    private JMenuItem createMenuItem(final String label, final String action, final ActionListener listener) {
        final JMenuItem item = new JMenuItem(label);
        item.setActionCommand(action);
        item.addActionListener(listener);
        return item;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("NEW")) {
            controller.newMap();
        } else if (e.getActionCommand().equals("LOAD")) {
            controller.loadMap();
        } else if (e.getActionCommand().equals("SAVE")) {
            controller.saveMap();
        } else if (e.getActionCommand().equals("SAVE")) {
            controller.saveMapAs();
        } else if (e.getActionCommand().equals("CLOSE")) {
            controller.closeMap();
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
    public Controller getController() {
        return controller;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

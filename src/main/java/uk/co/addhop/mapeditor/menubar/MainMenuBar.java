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
        menu.add(new JMenuItem("New...")).setActionCommand("NEW");
        menu.add(new JMenuItem("Open...")).setActionCommand("LOAD");
        menu.add(fileOpenRecentMenu);
        menu.add(new JMenuItem("Close Map")).setActionCommand("CLOSE");
        menu.addSeparator();
        menu.add(new JMenuItem("Settings..."));
        menu.addSeparator();
        menu.add(new JMenuItem("Save All")).setActionCommand("SAVEALL");

        menu.addActionListener(this);

        menuBar = new JMenuBar();
        menuBar.add(menu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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

package uk.co.addhop.mapeditor.toolbar;

import uk.co.addhop.mapeditor.interfaces.Controller;
import uk.co.addhop.mapeditor.interfaces.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

/**
 * ToolbarView
 * <p/>
 * Created by edwardaddley on 05/01/15.
 */
public class ToolbarView extends JToolBar implements View<JToolBar, ToolbarController>, ActionListener {

    private ToolbarController controller;

    public ToolbarView() {
        super();
    }

    public void makeToolbar(final ToolbarController controller) {

        this.controller = controller;

        makeButton("Undo", controller, "UNDO");
        makeButton("Redo", controller, "REDO");
        addSeparator();
        makeButton("Select", controller, "SELECT");
        makeButton("Deselect", controller, "DESELECT");
        addSeparator();
        makeButton("Paint", controller, "PAINT");
        makeButton("Select", controller, "SELECT");
        makeButton("Fill", controller, "FILL");
        makeButton("Magic ", controller, "MAGIC");
    }

    private JButton makeButton(final String label, final ToolbarController controller, final String actionCommand) {

        final JButton jButton = new JButton();
        jButton.setText(label);
        jButton.setActionCommand(actionCommand);
        jButton.addActionListener(this);
        add(jButton);

        return jButton;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("UNDO")) {
            // Call into the undo system
            controller.undo();
        } else if (e.getActionCommand().equals("REDO")) {
            // Call into the undo system
            controller.redo();
        } else if (e.getActionCommand().equals("FILL")) {
            // Set brush to fill
            controller.fill();
        }
    }

    @Override
    public JToolBar getView() {
        return this;
    }

    @Override
    public void setController(ToolbarController controller) {
        this.controller = controller;
    }

    @Override
    public Controller getController() {
        return controller;
    }
}

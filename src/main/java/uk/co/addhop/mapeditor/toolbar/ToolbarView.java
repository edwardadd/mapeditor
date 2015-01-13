package uk.co.addhop.mapeditor.toolbar;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * ToolbarView
 * <p/>
 * Created by edwardaddley on 05/01/15.
 */
public class ToolbarView extends JToolBar implements Observer {

    private JButton newButton;
    private JButton loadButton;
    private JButton saveButton;

    private JButton addTileSheetButton;
    private JButton helpButton;

    public ToolbarView() {
        super();
    }

    public void makeToolbar(final ToolbarController controller) {

//        this.setOpaque(false);

        newButton = makeButton("New...", controller, "newMap");
        loadButton = makeButton("Load...", controller, "loadMap");
        saveButton = makeButton("Save...", controller, "saveMap");
        addSeparator();
        addTileSheetButton = makeButton("Add...", controller, "addTileSet");
        addSeparator();
        helpButton = makeButton("Help", controller, "help");
    }

    private JButton makeButton(final String label, final ToolbarController controller, final String actionCommand) {

        final JButton jButton = new JButton();
        jButton.setText(label);
        jButton.setActionCommand(actionCommand);
        jButton.addActionListener(controller);
        add(jButton);

        return jButton;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

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

    private JButton mazeButton;
    private JButton worldButton;
    private JButton cityButton;

    private JButton selectButton;
    private JButton fillButton;
    private JButton magicButton;

    private JButton addTileSheetButton;
    private JButton viewLibraryButton;
    private JButton helpButton;

    public ToolbarView() {
        super();
    }

    public void makeToolbar(final ToolbarController controller) {

        mazeButton = makeButton("Generate Maze...", controller, "MAZE");
        worldButton = makeButton("Generate World...", controller, "WORLD");
        cityButton = makeButton("Generate City...", controller, "CITY");
        addSeparator();
        selectButton = makeButton("Select", controller, "SELECT");
        fillButton = makeButton("Fill", controller, "FILL");
        magicButton = makeButton("Magic ", controller, "MAGIC");
        addSeparator();
        addTileSheetButton = makeButton("Add Tileset...", controller, "addTileSet");
        viewLibraryButton = makeButton("View library...", controller, "viewLibrary");
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

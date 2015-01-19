package uk.co.addhop.mapeditor.menubar;

import uk.co.addhop.mapeditor.MainApplication;
import uk.co.addhop.mapeditor.dialogs.NewMapDialogViewController;
import uk.co.addhop.mapeditor.dialogs.TileSheetViewDialog;
import uk.co.addhop.mapeditor.interfaces.Controller;
import uk.co.addhop.mapeditor.models.Map;

import javax.swing.*;

/**
 * MainMenuBarController
 * <p/>
 * Created by edwardaddley on 17/01/15.
 */
public class MainMenuBarController implements Controller<MainApplication> {
    private MainApplication model;
    private MainApplication.MapWindow parent;

    @Override
    public void setModel(final MainApplication model) {
        this.model = model;
    }

    public void setParent(final MainApplication.MapWindow mapWindow) {
        parent = mapWindow;
    }

    public void newMap() {
        // Start new dialog and create a new Window

        final NewMapDialogViewController dialogViewController = new NewMapDialogViewController(null, model, true);
        dialogViewController.setVisible(true);
    }

    public void saveMap() {
        // Get currently focused window and tell it to save map

        final MainApplication.MapWindow window;

        if (parent != null) {
            window = parent;
        } else {
            window = model.getFocusedWindow();
        }

        if (window != null) {
            final Map map = window.getMap();
            if (map.getFileName() != null) {
                map.saveTileSet(map.getFileName());

                model.addToRecenList(map.getFileName());
            } else {
                saveMapAs();
            }
        }
    }

    public void loadMap() {
        // Start load dialog and create a new Window

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //System.out.println("You chose to open this file: " +
            final String filePath = chooser.getSelectedFile().getAbsolutePath();

            model.createMapWindow(new Map(filePath));
        }

    }

    public void closeMap() {
        // Get currently focused window
        // Close the window

        final MainApplication.MapWindow window;

        if (parent != null) {
            window = parent;
        } else {
            window = model.getFocusedWindow();
        }

        window.dispose();
    }

    public void saveMapAs() {
        final MainApplication.MapWindow window;

        if (parent != null) {
            window = parent;
        } else {
            window = model.getFocusedWindow();
        }

        if (window != null) {

            final JFileChooser chooser = new JFileChooser();
            final int returnVal = chooser.showSaveDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                //System.out.println("You chose to open this file: " +
                final String filePath = chooser.getSelectedFile().getAbsolutePath();
                window.getMap().saveTileSet(filePath);

                // Add to most recent file list
                model.addToRecenList(window.getMap().getFileName());
            }
        }
    }

    public void addTileSheet() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Note: source for ExampleFileFilter can be found in FileChooserDemo,
        // under the demo/jfc directory in the Java 2 SDK, Standard Edition.
        //ExampleFileFilter filter = new ExampleFileFilter();
        //filter.addExtension("jpg");
        //filter.addExtension("gif");
        //filter.setDescription("JPG & GIF Images");
        //chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //System.out.println("You chose to open this file: " +
            final String filePath = chooser.getSelectedFile().getAbsolutePath();

            // Display modal dialog for cutting up the
            final TileSheetViewDialog dialog = new TileSheetViewDialog(null, "Tile Sheet View Dialog", true, filePath, model.getDatabase());
            dialog.setVisible(true);
        }
    }
}

package uk.co.addhop.mapeditor.menubar;

import uk.co.addhop.mapeditor.MainApplication;
import uk.co.addhop.mapeditor.dialogs.NewMapDialogViewController;
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

    @Override
    public void setModel(final MainApplication model) {
        this.model = model;
    }

    public void newMap() {
        // Start new dialog and create a new Window

        final NewMapDialogViewController dialogViewController = new NewMapDialogViewController(null, model, true);
        dialogViewController.setVisible(true);
    }

    public void saveMap() {
        // Get currently focused window and tell it to save map

        final MainApplication.MapWindow window = model.getFocusedWindow();

        if (window != null) {
            final Map map = window.getMap();
            if (map.getFileName() != null) {
                map.saveTileSet(map.getFileName());
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
    }

    public void saveMapAs() {
        final MainApplication.MapWindow window = model.getFocusedWindow();

        if (window != null) {

            final JFileChooser chooser = new JFileChooser();
            final int returnVal = chooser.showSaveDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                //System.out.println("You chose to open this file: " +
                final String filePath = chooser.getSelectedFile().getAbsolutePath();
                window.getMap().saveTileSet(filePath);

                // Add to most recent file list
            }
        }
    }
}

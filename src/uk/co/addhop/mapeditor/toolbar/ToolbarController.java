package uk.co.addhop.mapeditor.toolbar;

import uk.co.addhop.mapeditor.dialogs.NewMapDialogViewController;
import uk.co.addhop.mapeditor.dialogs.TielSheetViewDialog;
import uk.co.addhop.mapeditor.map.MapModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ToolbarController
 * <p/>
 * Created by edwardaddley on 05/01/15.
 */
public class ToolbarController implements ActionListener {

    private ToolbarModel toolbarModel;
    private MapModel mapModel;

    public ToolbarController(final ToolbarModel toolbarModel, final MapModel mapModel) {
        this.toolbarModel = toolbarModel;
        this.mapModel = mapModel;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {

        if (e.getActionCommand().equals("newMap")) {
            NewMapDialogViewController dialogViewController = new NewMapDialogViewController(null, mapModel, true);
            dialogViewController.setVisible(true);
        }

        if (e.getActionCommand().equals("addTileSet")) {
            //javax.swing.JFileDialog fd = new javax.swing.JFileDialog(this);
            //fd.setVisible(true);
            //String filename = fd.getDirectory()+fd.getFile();

            //filesetField.setText(filename);

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
                TielSheetViewDialog dialog = new TielSheetViewDialog(null, "Tile Sheet View Dialog", true, filePath);
                dialog.setVisible(true);
            }
        }
    }
}

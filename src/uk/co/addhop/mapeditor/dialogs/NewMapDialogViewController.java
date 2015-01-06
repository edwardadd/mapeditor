package uk.co.addhop.mapeditor.dialogs;

import uk.co.addhop.mapeditor.map.MapModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewMapDialogViewController extends javax.swing.JDialog implements ActionListener {
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField descField;
    private javax.swing.JButton okButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField widthField;
    private javax.swing.JTextField heightField;
    private javax.swing.JTextField filesetField;
    private javax.swing.JButton filesetButton;

    private MapModel parent;

    public NewMapDialogViewController(final JFrame owner, final MapModel _parent, final boolean modal) {
        super(owner, "New Map...", modal);
        parent = _parent;

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        widthField = new javax.swing.JTextField(10);
        heightField = new javax.swing.JTextField(10);
        //javax.swing.JSpinner.NumberEditor(widthSpinner);
        //javax.swing.JSpinner.NumberEditor(heightSpinner);
        nameField = new javax.swing.JTextField(10);
        descField = new javax.swing.JTextField(10);
        filesetField = new javax.swing.JTextField(10);
        filesetButton = new javax.swing.JButton();

        //set layout
        java.awt.GridBagConstraints gridBagConstraints;
        getContentPane().setLayout(new java.awt.GridBagLayout());
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        getContentPane().add(new JLabel("Name :"), gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        getContentPane().add(nameField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        getContentPane().add(new JLabel("Description :"), gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        getContentPane().add(descField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        getContentPane().add(new JLabel("Width :"), gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        getContentPane().add(widthField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        getContentPane().add(new JLabel("Height :"), gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        getContentPane().add(heightField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        getContentPane().add(new JLabel("FileSet :"), gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        getContentPane().add(filesetField, gridBagConstraints);

        filesetButton.setText("...");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        getContentPane().add(filesetButton, gridBagConstraints);
        filesetButton.addActionListener(this);

        okButton.setText("Ok");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        //gridBagConstraints.gridwidth = 3;
        getContentPane().add(okButton, gridBagConstraints);
        okButton.addActionListener(this);

        cancelButton.setText("Cancel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        //gridBagConstraints.gridwidth = 3;
        getContentPane().add(cancelButton, gridBagConstraints);
        cancelButton.addActionListener(this);

        pack();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            parent.createMap(nameField.getText(), Integer.parseInt(widthField.getText()), Integer.parseInt(heightField.getText()));
            dispose();
        }

        if (e.getSource() == cancelButton) {
            dispose();
        }

        if (e.getSource() == filesetButton) {
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
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                //System.out.println("You chose to open this file: " +
                String t = chooser.getSelectedFile().getAbsolutePath();
                filesetField.setText(t);
            }

        }
    }
}

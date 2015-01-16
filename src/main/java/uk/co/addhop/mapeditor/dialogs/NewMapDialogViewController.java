package uk.co.addhop.mapeditor.dialogs;

import uk.co.addhop.mapeditor.models.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewMapDialogViewController extends javax.swing.JDialog implements ActionListener {
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField descField;
    private javax.swing.JButton okButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField widthField;
    private javax.swing.JTextField heightField;
    private javax.swing.JTextField tileWidthField;
    private javax.swing.JTextField tileHeightField;

    private final Component[] tabOrder = new Component[]{
            nameField,
            descField,
            widthField,
            heightField,
            tileWidthField,
            tileHeightField,
            okButton,
            cancelButton
    };

    private Map parent;

    public NewMapDialogViewController(final JFrame owner, final Map _parent, final boolean modal) {
        super(owner, "New Map...", modal);
        parent = _parent;

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        widthField = new javax.swing.JTextField("10", 10);
        heightField = new javax.swing.JTextField("10", 10);
        nameField = new javax.swing.JTextField("New Map", 10);
        descField = new javax.swing.JTextField("A description...", 10);
        tileWidthField = new javax.swing.JTextField("50", 10);
        tileHeightField = new javax.swing.JTextField("50", 10);

        //set layout
        java.awt.GridBagConstraints gridBagConstraints;
        getContentPane().setLayout(new java.awt.GridBagLayout());
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        getContentPane().setFocusTraversalPolicy(new FocusTraversalPolicy() {
            @Override
            public Component getComponentAfter(Container aContainer, Component aComponent) {
                for (int i = 0; i < tabOrder.length; i++) {
                    Component component = tabOrder[i];
                    if (aComponent.equals(component)) {
                        if (i == tabOrder.length - 1) {
                            return tabOrder[0];
                        }
                        return tabOrder[i + 1];
                    }
                }

                return null;
            }

            @Override
            public Component getComponentBefore(Container aContainer, Component aComponent) {
                for (int i = 0; i < tabOrder.length; i++) {
                    Component component = tabOrder[i];
                    if (aComponent.equals(component)) {
                        if (i == 0) {
                            return tabOrder[tabOrder.length - 1];
                        }
                        return tabOrder[i - 1];
                    }
                }

                return null;
            }

            @Override
            public Component getFirstComponent(Container aContainer) {
                return nameField;
            }

            @Override
            public Component getLastComponent(Container aContainer) {
                return cancelButton;
            }

            @Override
            public Component getDefaultComponent(Container aContainer) {
                return okButton;
            }
        });

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
        getContentPane().add(new JLabel("Map Width :"), gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        getContentPane().add(widthField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        getContentPane().add(new JLabel("Map Height :"), gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        getContentPane().add(heightField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        getContentPane().add(new JLabel("Tile Width :"), gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        getContentPane().add(tileWidthField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        getContentPane().add(new JLabel("Tile Height :"), gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        getContentPane().add(tileHeightField, gridBagConstraints);

        okButton.setText("Ok");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        //gridBagConstraints.gridwidth = 3;
        getContentPane().add(okButton, gridBagConstraints);
        okButton.addActionListener(this);

        cancelButton.setText("Cancel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        //gridBagConstraints.gridwidth = 3;
        getContentPane().add(cancelButton, gridBagConstraints);
        cancelButton.addActionListener(this);

        pack();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            // TODO Validation on text fields
            parent.createMap(nameField.getText(),
                    Integer.parseInt(widthField.getText()), Integer.parseInt(heightField.getText()),
                    Integer.parseInt(tileWidthField.getText()), Integer.parseInt(tileHeightField.getText()));
            dispose();
        }

        if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}

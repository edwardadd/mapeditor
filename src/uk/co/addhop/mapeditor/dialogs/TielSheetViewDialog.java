package uk.co.addhop.mapeditor.dialogs;

import uk.co.addhop.mapeditor.TileSheet;
import uk.co.addhop.mapeditor.TileTypeDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * TielSheetViewDialog
 * <p/>
 * Created by edwardaddley on 06/01/15.
 */
public class TielSheetViewDialog extends JDialog implements ActionListener {

    private TileTypeDatabase database;

    private int gridWidth;
    private int gridHeight;

    private TileSheet tileSheet;
    private TileSheetView tileSheetView;

    public TielSheetViewDialog(Frame owner, String title, boolean modal, String tileSheetFilename) {
        super(owner, title, modal);

        makeView();

        loadTileSheet(tileSheetFilename);
    }

    private void loadTileSheet(String tileSheetFilename) {

        System.out.println("loadTileSheet - " + tileSheetFilename);

        final ImageIcon image = TileTypeDatabase.loadImage(tileSheetFilename);

        tileSheet = new TileSheet();
        tileSheet.setImage(image.getImage());

        tileSheetView.setImage(image);
        tileSheetView.repaint();

        if (image.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.err.println("File not found: " + tileSheetFilename);
        }
    }

    private void makeView() {
        setSize(640, 480);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tileSheetView = new TileSheetView();

        FlowLayout layout = new FlowLayout();
//        layout.addLayoutComponent("tile", tileSheetView);
        add(tileSheetView);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("CANCEL");

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

//        layout.addLayoutComponent("ok", okButton);
//        layout.addLayoutComponent("cancel", cancelButton);
        add(okButton);
        add(cancelButton);

        setLayout(layout);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("OK")) {
        }

        dispose();
    }

    public static class TileSheetView extends JPanel {
        private ImageIcon image;

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponents(g);

            g.setColor(Color.BLUE);
            g.fillRect(0, 0, getWidth(), getHeight());

            if (image.getImageLoadStatus() == MediaTracker.COMPLETE) {
                g.drawImage(image.getImage(), 0, 0, null);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 100);
        }

        public void setImage(ImageIcon image) {
            this.image = image;
        }
    }
}

package uk.co.addhop.mapeditor.dialogs;

import uk.co.addhop.mapeditor.models.Map;
import uk.co.addhop.mapeditor.models.TileSheet;
import uk.co.addhop.mapeditor.models.TileTypeDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * TielSheetViewDialog
 * <p/>
 * Created by edwardaddley on 06/01/15.
 */
public class TileSheetViewDialog extends JDialog implements ActionListener {

    private TileTypeDatabase database;

    private TileSheet tileSheet;
    private TileSheetView tileSheetView;

    private JTextField widthText;
    private JTextField heightText;

    public TileSheetViewDialog(Frame owner, String title, boolean modal, String tileSheetFilename, Map mapModel) {
        super(owner, title, modal);

        database = mapModel.getDatabase();

        makeView();

        loadTileSheet(tileSheetFilename);
    }

    private void loadTileSheet(String tileSheetFilename) {

        System.out.println("loadTileSheet - " + tileSheetFilename);

        final ImageIcon image = TileTypeDatabase.loadImage(tileSheetFilename);

        tileSheet = new TileSheet(tileSheetFilename);
        tileSheet.setImage(image.getImage());

        tileSheetView.setImage(image);
//        tileSheetView.repaint();

        if (image.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.err.println("File not found: " + tileSheetFilename);
        }
    }

    private void makeView() {
        setSize(640, 480);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        final JLabel widthLabel = new JLabel("Tile Width:");
        final JLabel heightLabel = new JLabel("Tile Height:");
        widthText = new JTextField("50", 10);
        heightText = new JTextField("50", 10);

        final JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(widthLabel);
        topPanel.add(widthText);
        topPanel.add(heightLabel);
        topPanel.add(heightText);

        add(topPanel, BorderLayout.PAGE_START);

        tileSheetView = new TileSheetView();
        add(tileSheetView, BorderLayout.CENTER);

        final JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");

        final JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("CANCEL");

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        final JPanel panel = new JPanel(new FlowLayout());

        panel.add(okButton);
        panel.add(cancelButton);

        add(panel, BorderLayout.PAGE_END);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("OK")) {
            java.util.List<TileSheet.Cell> sheet = tileSheet.getCellList();
            sheet.clear();

            final int imageWidth = tileSheet.getImage().getWidth(null);
            final int imageHeight = tileSheet.getImage().getHeight(null);

            final int tileWidth = Integer.parseInt(widthText.getText());
            final int tileHeight = Integer.parseInt(heightText.getText());

//            final int remainderWidth = imageWidth % tileWidth;
//            final int remainderHeight = imageHeight % imageHeight;

            int y = 0;
            while (y < imageHeight) {

                int x = 0;
                while (x < imageWidth) {

                    // Todo sort out non uniformly set width and height images
                    tileSheet.addCell(x, y, tileWidth, tileHeight);
                    x += tileWidth;
                }
                y += tileHeight;
            }

            database.addTileSheet(tileSheet.getFilename(), tileSheet);

            database.saveDatabase();
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

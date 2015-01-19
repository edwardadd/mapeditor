package uk.co.addhop.mapeditor.dialogs;

import uk.co.addhop.mapeditor.models.TileSheet;
import uk.co.addhop.mapeditor.models.TileTypeDatabase;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * TielSheetViewDialog
 * <p/>
 * Created by edwardaddley on 06/01/15.
 */
public class TileSheetViewDialog extends JDialog implements ActionListener, DocumentListener {

    private TileTypeDatabase database;

    private TileSheet tileSheet;
    private TileSheetView tileSheetView;

    private JTextField widthText;
    private JTextField heightText;

    public TileSheetViewDialog(final Frame owner, final String title, final boolean modal, final String tileSheetFilename, final TileTypeDatabase database) {
        super(owner, title, modal);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.database = database;

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

        widthText.getDocument().addDocumentListener(this);
        heightText.getDocument().addDocumentListener(this);

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

            try {

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

                dispose();
            } catch (NumberFormatException ex) {
                System.err.println("Width and Height must be valid numbers");
            }
        } else {
            dispose();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        textChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        textChanged();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        textChanged();
    }

    private void textChanged() {
        System.out.println("textChanged");
        try {
            final int width = Integer.parseInt(widthText.getText());
            final int height = Integer.parseInt(heightText.getText());

            if (width > 0 && height > 0) {
                tileSheetView.setTileSize(width, height);
            }
        } catch (NumberFormatException ex) {
            // Prevent action
        }
    }

    public static class TileSheetView extends JPanel {
        private ImageIcon image;
        private int tileWidth = 50;
        private int tileHeight = 50;

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponents(g);

            g.setColor(Color.BLUE);
            g.fillRect(0, 0, getWidth(), getHeight());

            if (image.getImageLoadStatus() == MediaTracker.COMPLETE) {
                g.drawImage(image.getImage(), 0, 0, null);
            }

            g.setColor(Color.RED);

            for (int x = 0; x < image.getIconWidth(); x += tileWidth) {
                g.drawLine(x, 0, x, image.getIconHeight());
            }

            for (int y = 0; y < image.getIconHeight(); y += tileHeight) {
                g.drawLine(0, y, image.getIconWidth(), y);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 100);
        }

        public void setImage(ImageIcon image) {
            this.image = image;
        }

        public void setTileSize(int width, int height) {
            tileWidth = width;
            tileHeight = height;

            repaint();
        }
    }
}

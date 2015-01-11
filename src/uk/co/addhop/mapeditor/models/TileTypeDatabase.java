package uk.co.addhop.mapeditor.models;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * TileTypeDatabase
 * <p/>
 * Created by edwardaddley on 05/01/15.
 */
public class TileTypeDatabase extends Observable {

    private Map<String, TileSheet> tileSheetList;

    public TileTypeDatabase() {
        tileSheetList = new HashMap<String, TileSheet>();
        createDefaultTileSheet();
    }

    public void createDefaultTileSheet() {
        final TileSheet tileSheet = new TileSheet();
        tileSheet.setFilename("Default");

        final BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        final Graphics graphic = image.getGraphics();
        graphic.setColor(Color.GREEN);
        graphic.fillRect(0, 0, 50, 50);
        graphic.setColor(Color.BLACK);
        graphic.drawRect(1, 1, 47, 47);
        tileSheet.setImage(image);
        tileSheet.addCell(0, 0, 50, 50);

        tileSheetList.put("Default", tileSheet);

        setChanged();
    }

    public void loadDatabase() {
        // TODO Load data
        setChanged();

        notifyObservers();
    }

    public void saveDatabase() {
        // TODO Save data
        notifyObservers();
    }

    public static ImageIcon loadImage(final String filename) {
        final java.net.URL imageURL = TileTypeDatabase.class.getClassLoader().getResource(filename);

        if (imageURL != null) {
            return new ImageIcon(imageURL);
        } else { // file is not a inside the jar file
            System.out.println("File not found in package : " + filename);
            return new ImageIcon(filename);
        }
    }

    public void addTileSheet(String name, TileSheet tileSheet) {
        tileSheetList.put(name, tileSheet);

        setChanged();
        notifyObservers();
    }

    public TileSheet getTileSheet(String name) {
        return tileSheetList.get(name);
    }

    /**
     * getListOfDisplayCells
     *
     * @return
     */
    public java.util.List<DisplayCell> getListOfDisplayCells() {
        ArrayList<DisplayCell> displayCells = new ArrayList<DisplayCell>();
        for (TileSheet sheet : tileSheetList.values()) {
            for (TileSheet.Cell cell : sheet.getSheet()) {
                DisplayCell displayCell = new DisplayCell();
                displayCell.setImage(sheet.getImage());
                displayCell.setFrame(cell.getFrame());
                displayCell.setTileSheetName(sheet.getFilename());

                displayCells.add(displayCell);
            }
        }

        return displayCells;
    }

    public static class DisplayCell {
        private Rectangle frame;
        private Image image;
        private String tileSheetName;

        public Rectangle getFrame() {
            return frame;
        }

        public void setFrame(Rectangle frame) {
            this.frame = frame;
        }

        public Image getImage() {
            return image;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public String getTileSheetName() {
            return tileSheetName;
        }

        public void setTileSheetName(String tileSheetName) {
            this.tileSheetName = tileSheetName;
        }
    }
}

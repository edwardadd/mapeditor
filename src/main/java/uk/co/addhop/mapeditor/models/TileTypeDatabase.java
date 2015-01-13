package uk.co.addhop.mapeditor.models;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Vector;

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
        } else { // file is not inside the jar file
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
    public java.util.Vector<DisplayCell> getListOfDisplayCells() {
        final Vector<DisplayCell> displayCells = new Vector<DisplayCell>();
        for (TileSheet sheet : tileSheetList.values()) {

            final java.util.List<TileSheet.Cell> sheet1 = sheet.getCellList();

            for (int i = 0; i < sheet1.size(); i++) {
                final TileSheet.Cell cell = sheet1.get(i);

                final DisplayCell displayCell = new DisplayCell();
                displayCell.setFrame(cell.getFrame());
                displayCell.setTileSheetName(sheet.getFilename());
                displayCell.setIndex(i);

                displayCells.add(displayCell);
            }
        }

        return displayCells;
    }

    public static class DisplayCell {
        private Rectangle frame;
        private String tileSheetName;
        private int index;

        public Rectangle getFrame() {
            return frame;
        }

        public void setFrame(Rectangle frame) {
            this.frame = frame;
        }

        public String getTileSheetName() {
            return tileSheetName;
        }

        public void setTileSheetName(String tileSheetName) {
            this.tileSheetName = tileSheetName;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}

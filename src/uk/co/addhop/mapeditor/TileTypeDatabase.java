package uk.co.addhop.mapeditor;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * TileTypeDatabase
 * <p/>
 * Created by edwardaddley on 05/01/15.
 */
public class TileTypeDatabase {

    private Map<String, TileSheet> tileSheetList;

    public TileTypeDatabase() {
        tileSheetList = new HashMap<String, TileSheet>();
    }

    private void cacheTileSheets() {
        // TODO
    }

    public Image getTileImage(final String name, final int index) {
        final TileSheet sheet = tileSheetList.get(name);
        final TileSheet.Cell cell = sheet.getSheet().get(index);

        Image image = cell.getCachedImage();
        if (image == null) {
            image = sheet.getImage();
        }
        return image;
    }

    public Image getTileImage(final int hash) {
        // TODO
        return null;
    }

    public void loadDatabase() {

    }

    public void saveDatabase() {

    }

    public static ImageIcon loadImage(final String filename) {
        final java.net.URL imageURL = TileTypeDatabase.class.getClassLoader().getResource(filename);

        if (imageURL != null) {
            return new ImageIcon(imageURL);
        } else { // file is not a inside the jar file
            System.err.println("Couldn't find file: " + filename);
            return new ImageIcon(filename);
        }
    }
}

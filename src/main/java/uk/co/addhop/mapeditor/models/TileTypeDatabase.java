package uk.co.addhop.mapeditor.models;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import uk.co.addhop.mapeditor.MainApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        final TileSheet tileSheet = new TileSheet("Default");

        final BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        final Graphics graphic = image.getGraphics();
        graphic.setColor(Color.GREEN);
        graphic.fillRect(0, 0, 50, 50);
        graphic.setColor(Color.BLACK);
        graphic.drawRect(1, 1, 47, 47);

        tileSheet.setImage(image);
        tileSheet.addCell(0, 0, 50, 50);

        tileSheetList.put(tileSheet.getFilename(), tileSheet);

        setChanged();
    }

    public void loadDatabase() {

        final Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getDeclaredType() == Image.class;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return aClass == Image.class;
            }
        }).create();

        final FileReader reader;
        try {
            reader = new FileReader(MainApplication.documentPath + "/palette.json");

//            final String json = readWholeFile(reader);
            final JsonParser parser = new JsonParser();
            final JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setLenient(true);

            final JsonArray array = parser.parse(jsonReader).getAsJsonArray();

            for (JsonElement element : array) {
                final TileSheet sheet = gson.fromJson(element, TileSheet.class);
                sheet.setImage(TileTypeDatabase.loadImage(sheet.getFilename()).getImage());
                tileSheetList.put(sheet.getFilename(), sheet);
            }

            reader.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        setChanged();

        notifyObservers();
    }

    public void saveDatabase() {
        final Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getDeclaredType() == Image.class;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return aClass == Image.class;
            }
        }).create();

        final FileWriter writer;
        try {
            writer = new FileWriter(MainApplication.documentPath + "/palette.json");
            final JsonWriter jsonWriter = new JsonWriter(writer);
            jsonWriter.setIndent("\t");

            jsonWriter.beginArray();
            for (TileSheet sheet : tileSheetList.values()) {
                if (!sheet.getFilename().equals("Default")) {
                    gson.toJson(sheet, TileSheet.class, jsonWriter);
                }
            }
            jsonWriter.endArray();

            jsonWriter.flush();
            jsonWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

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

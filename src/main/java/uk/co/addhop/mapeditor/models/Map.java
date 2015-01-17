package uk.co.addhop.mapeditor.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Map extends Observable {
    private String mapName;
    private String fileName;

    private List<Tile> mapTiles;

    private int mapWidth;
    private int mapHeight;

    private int tileWidth;
    private int tileHeight;

    private TileTypeDatabase database;

    public Map() {
        super();

        mapName = "New Map";

        mapWidth = 10;
        mapHeight = 10;

        tileWidth = 50;
        tileHeight = 50;

        mapTiles = new ArrayList<Tile>(mapWidth * mapHeight);
    }

    public Map(final String fileName) {
        super();

        this.fileName = fileName;

        loadTileSet(fileName);
    }

    public void initialize() {

        // Load previous map or start empty

        notifyChanges();
    }

    public void loadTileSet(final String filename) {

        final Reader reader;

        try {
            reader = new FileReader(filename);
            final JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setLenient(true);

            final Gson gson = new GsonBuilder().create();

            jsonReader.beginObject();
            jsonReader.nextName();
            mapName = jsonReader.nextString();
            jsonReader.nextName();
            mapWidth = jsonReader.nextInt();
            jsonReader.nextName();
            mapHeight = jsonReader.nextInt();
            jsonReader.nextName();
            tileWidth = jsonReader.nextInt();
            jsonReader.nextName();
            tileHeight = jsonReader.nextInt();
            jsonReader.nextName();

            if (mapTiles == null) {
                mapTiles = new ArrayList<Tile>(mapWidth * mapHeight);
            }

            mapTiles.clear();

            jsonReader.beginArray();

            for (int i = 0; i < mapWidth * mapHeight; i++) {
                final Tile tile = gson.fromJson(jsonReader, Tile.class);
                mapTiles.add(tile);
            }

            jsonReader.endArray();
            jsonReader.endObject();

            jsonReader.close();

            notifyChanges();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveTileSet(final String filename) {
        this.fileName = filename;

        final Writer writer;
        try {
            writer = new FileWriter(filename);

            final Gson gson = new GsonBuilder().create();
            final JsonWriter jsonWriter = new JsonWriter(writer);
            jsonWriter.setLenient(true);

            jsonWriter.beginObject();

            jsonWriter.name("Name");
            jsonWriter.value(mapName);

            jsonWriter.name("Map Width");
            jsonWriter.value(mapWidth);

            jsonWriter.name("Map Height");
            jsonWriter.value(mapHeight);

            jsonWriter.name("Tile Width");
            jsonWriter.value(tileWidth);

            jsonWriter.name("Tile Height");
            jsonWriter.value(tileHeight);

            jsonWriter.name("Tiles");
            jsonWriter.beginArray();
            for (Tile tile : mapTiles) {
                gson.toJson(tile, Tile.class, jsonWriter);
            }
            jsonWriter.endArray();
            jsonWriter.endObject();
            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void createMap(final String mapName, final int width, final int height, final int tileWidth, final int tileHeight) {
        this.mapName = mapName;
        this.mapWidth = width;
        this.mapHeight = height;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        mapTiles.clear();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final Tile tile = new Tile(x, y);
                tile.setTileSetIndex(0);
                tile.setTileSheet("Default");

                mapTiles.add(tile);
            }
        }

        notifyChanges();
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public String getMapName() {
        return mapName;
    }

    public String getFileName() {
        return fileName;
    }

    public List<Tile> getMapTiles() {
        return mapTiles;
    }

    public TileTypeDatabase getDatabase() {
        return database;
    }

    public void setDatabase(TileTypeDatabase database) {
        this.database = database;
    }

    private void notifyChanges() {

        setChanged();

//        notifyObservers(this);
    }

    public Tile getTile(int x, int y) {
        final int index = x + y * mapWidth;

        if (index < mapTiles.size()) {
            return mapTiles.get(index);
        }

        return null;
    }
}

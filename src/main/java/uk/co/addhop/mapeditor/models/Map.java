package uk.co.addhop.mapeditor.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Map extends Observable {
    private String mapName;

    private List<Tile> mapTiles;
    private int mapWidth;
    private int mapHeight;

    private int tileWidth;
    private int tileHeight;

    private TileTypeDatabase database;

    public Map() {
        super();

        mapName = "New Tile Set.tst";

        mapWidth = 10;
        mapHeight = 10;

        tileWidth = 50;
        tileHeight = 50;

        mapTiles = new ArrayList<Tile>(mapWidth * mapHeight);
    }

    public Map(final String filename) {
        super();

        mapName = filename;

        loadTileSet(filename);
    }

    public void initialize() {

        // Load previous map or start empty

        notifyChanges();
    }

    public void loadTileSet(final String filename) {

    }

    public void saveTileSet(final String filename) {

    }

    public void createMap(String mapName, int width, int height) {
        this.mapName = mapName;
        this.mapWidth = width;
        this.mapHeight = height;

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

    public TileTypeDatabase getDatabase() {
        return database;
    }

    public void setDatabase(TileTypeDatabase database) {
        this.database = database;
    }

    private void notifyChanges() {
        notifyData.width = tileWidth;
        notifyData.height = tileHeight;
        notifyData.tileList = mapTiles;

        setChanged();

        notifyObservers(notifyData);
    }

    public static NotifyData notifyData = new NotifyData();

    public Tile getTile(int x, int y) {
        final int index = x + y * mapWidth;

        if (index < mapTiles.size()) {
            return mapTiles.get(index);
        }

        return null;
    }

    public static class NotifyData {
        public int width;
        public int height;

        public List<Tile> tileList;
    }
}

package uk.co.addhop.mapeditor.models;

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
        fileName = "New Tile Set.tst";

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

    }

    public void saveTileSet(final String filename) {

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

        notifyObservers(this);
    }

    public Tile getTile(int x, int y) {
        final int index = x + y * mapWidth;

        if (index < mapTiles.size()) {
            return mapTiles.get(index);
        }

        return null;
    }
}

package uk.co.addhop.mapeditor.map;

import uk.co.addhop.mapeditor.Tile;
import uk.co.addhop.mapeditor.palette.TileTypeDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MapModel extends Observable {
    private String mapName;

    private List<Tile> mapTiles;
    private int mapWidth;
    private int mapHeight;

    private int tileWidth;
    private int tileHeight;

    private TileTypeDatabase database;

    private MapView view;


    public MapModel() {
        super();

        mapName = "New Tile Set.tst";

        mapWidth = 10;
        mapHeight = 10;

        tileWidth = 50;
        tileHeight = 50;

        mapTiles = new ArrayList<Tile>(mapWidth * mapHeight);
    }

    public MapModel(final String filename) {
        super();

        mapName = filename;

        loadTileSet(filename);
    }

    public void initialize(final MapView view) {
        this.view = view;

        // Load previous map or start empty

        notifyChanges();
    }

    public void loadTileSet(String filename) {

    }

    public void saveTileSet(String filename) {

    }

    public void createMap(String mapName, int width, int height) {
        this.mapName = mapName;

        mapTiles.clear();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final Tile tile = new Tile();
                tile.setXPosition(x);
                tile.setYPosition(y);

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

    public static class NotifyData {
        public int width;
        public int height;

        public List<Tile> tileList;
    }
}

package uk.co.addhop.mapeditor.map;

import uk.co.addhop.mapeditor.Tile;
import uk.co.addhop.mapeditor.TileTypeDatabase;

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

        mapTiles = new ArrayList<Tile>(mapWidth * mapHeight);
    }

    public MapModel(final String filename) {
        super();

        mapName = filename;

        loadTileSet(filename);
    }

    public void initialize(final MapView view) {
        this.view = view;

        notifyObservers(mapTiles);
    }

    public void loadTileSet(String filename) {

    }

    public void saveTileSet(String filename) {

    }
}

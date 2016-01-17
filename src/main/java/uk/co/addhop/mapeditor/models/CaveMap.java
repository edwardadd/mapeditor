package uk.co.addhop.mapeditor.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A cave generating map
 * <p/>
 * Created by edwardaddley on 17/01/16.
 */
public class CaveMap extends Map {

    public CaveMap(int width, int height) {
        createMap("Cave", width, height, 8, 8);
    }

    @Override
    public void initialize() {
        super.initialize();

        generateNoise();
        processRules(5);
    }

    private void processRules(int maxIterations) {
        List<Tile> newTileList = new ArrayList<Tile>(getMapWidth() * getMapHeight());
        List<Tile> currentTileList = newTileList;
        List<Tile> previousTileList = getMapTiles();

        for (int y = 0; y < getMapHeight(); y++) {
            for (int x = 0; x < getMapWidth(); x++) {
                Tile tile = getTile(x, y);
                Tile copy = tile.createCopy();
                newTileList.add(x + y * getMapWidth(), copy);
            }
        }

        for (int i = 0; i < maxIterations; i++) {
            for (int y = 0; y < getMapHeight(); y++) {
                for (int x = 0; x < getMapWidth(); x++) {
                    Tile tile = previousTileList.get(x + y * getMapWidth());

                    int index = processTile(previousTileList, tile, x, y);

                    currentTileList.get(x + y * getMapWidth()).setTileSetIndex(index);
                }
            }

            List<Tile> temp = currentTileList;
            currentTileList = previousTileList;
            previousTileList = temp;
        }

        // Copy the final result back into the main tile set list if needs be
        if (previousTileList == newTileList) {
            for (int y = 0; y < getMapHeight(); y++) {
                for (int x = 0; x < getMapWidth(); x++) {
                    Tile tile = previousTileList.get(x + y * getMapWidth());
                    currentTileList.get(x + y * getMapWidth()).setTileSetIndex(tile.getTileSetIndex());
                }
            }
        }
    }

    private int processTile(List<Tile> list, Tile tile, int x, int y) {
        int filled = 0;
        for (int dy = y - 1; dy <= y + 1; dy++) {
            for (int dx = x - 1; dx <= x + 1; dx++) {

                if (dx < 0 || dx >= getMapWidth() || dy < 0 || dy >= getMapHeight()) {
                    filled++;
                    continue;
                }

                Tile temp = list.get(dx + dy * getMapWidth());

                if (temp.getTileSetIndex() > 0) {
                    filled++;
                }
            }
        }

        if (filled >= 5) {
            return 1;
        }

        return 0;
    }

    private void generateNoise() {
        Random random = new Random();

        for (int y = 0; y < getMapHeight(); y++) {
            for (int x = 0; x < getMapWidth(); x++) {
                Tile tile = getTile(x, y);
                tile.setTileSetIndex(random.nextBoolean() ? 1 : 0);
            }
        }
    }
}

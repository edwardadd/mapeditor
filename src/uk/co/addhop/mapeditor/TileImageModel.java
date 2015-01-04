package uk.co.addhop.mapeditor;

import java.util.*;

public class TileImageModel implements TileImageController {
    private String tileSetName;
    private ArrayList tileImages;

    private TileImageView view;

    private int selectedTile;

    public TileImageModel() {
        selectedTile = 0;
        tileSetName = "New Tile Set.tst";

        tileImages = new ArrayList();
    }

    public TileImageModel(String filename) {
        super();

        tileSetName = filename;

        loadTileSet(filename);
    }

    public void initialize(TileImageView view) {
        this.addTile("ggrass.gif");
        this.view = view;
        view.updateTileSet(tileImages);
    }

    public String getFilename() {
        return tileSetName;
    }

    public void addTile(String filename) {
        int newIndex = tileImages.size();
        tileImages.add(new TileImage(newIndex, filename));
    }

    public void removeTile(int index) {
        if (index > -1 && index < tileImages.size()) {
            if (selectedTile == index) selectedTile = 0;
            tileImages.remove(index);

            //TO DO: Re arrange the index of the separate tileImages
        }
    }

    public TileImage getTileImage(int index) {
        if (index > -1 && index < tileImages.size())
            return (TileImage) tileImages.get(index);
        else
            return null;
    }

    public int getTileTotal() {
        return tileImages.size();
    }

    public void setSelectedTile(int i) {
        if (i >= 0 && i < getTileTotal()) selectedTile = i;
    }

    public int getSelectedTileIndex() {
        return selectedTile;
    }

    public void loadTileSet(String filename) {

    }

    public void saveTileSet(String filename) {

    }

    public TileImage getSelectedTile() {
        return getTileImage(selectedTile);
    }
}

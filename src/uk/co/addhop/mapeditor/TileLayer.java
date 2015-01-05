package uk.co.addhop.mapeditor;

import java.util.ArrayList;
import java.util.List;

public class TileLayer {
    private List<Tile> tiles;
    private int       width;
    private int       height;
    private int       layerIndex;
        
    public TileLayer(int index, int width, int height) {
        tiles = new ArrayList<Tile>(width*height);
        layerIndex = index;
        this.width = width;
        this.height = height;
    }
    
    public void setLayerIndex( int index ) {
        layerIndex = index;
    }
    
    public int getLayerIndex() {
        return layerIndex;
    }
    
    public Tile getTile(int index) {
        return tiles.get(index);
    }
    
    public void setTile(int index, Tile val) {
        tiles.set(index, val);
    }
    
    public void addTile(Tile val) {
        tiles.add(val);
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}

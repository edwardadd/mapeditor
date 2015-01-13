package uk.co.addhop.mapeditor.models;

/**
 * An individual tile on the map, contains TileSet for drawing the right tile.
 */
public class Tile {

    /* Tile sheet name */
    private String tileSheet;

    /* The index of the Tile from a tile set */
    private int index;

    /* x and y co-ordinates within the map */
    private int xPosition;
    private int yPosition;

    /* the layer this tile is associated with */
//    private TileLayer mTileLayer;

    private Tile() {
        xPosition = -1;
        yPosition = -1;
        index = -1;
        tileSheet = null;
    }

    public Tile(final int x, final int y) {
        xPosition = x;
        yPosition = y;
    }

    public String getTileSheet() {
        return tileSheet;
    }

    public void setTileSheet(final String tileSheet) {
        this.tileSheet = tileSheet;
    }

    public int getTileSetIndex() {
        return index;
    }

    public void setTileSetIndex(int val) {
        index = val;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public Tile copy() {
        Tile ret = new Tile(0, 0);
        ret.xPosition = xPosition;
        ret.yPosition = yPosition;
        return ret;
    }

    public Tile copy(final Tile copyTo) {
        copyTo.xPosition = xPosition;
        copyTo.yPosition = yPosition;
        copyTo.setTileSetIndex(getTileSetIndex());
        return copyTo;
    }
}

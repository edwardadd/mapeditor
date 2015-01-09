package uk.co.addhop.mapeditor;

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

    public Tile() {
    }

    public Tile(final int x, final int y, final int index) {
        xPosition = x;
        yPosition = y;
        this.index = index;
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

    public void setXPosition(final int x) {
        xPosition = x;
    }

    public void setYPosition(final int y) {
        yPosition = y;
    }

    public Tile copy() {
        Tile ret = new Tile(0, 0, index);
        ret.setXPosition(getXPosition());
        ret.setYPosition(getYPosition());
        return ret;
    }

    public Tile copy(final Tile copyTo) {
        copyTo.setXPosition(getXPosition());
        copyTo.setYPosition(getYPosition());
        copyTo.setTileSetIndex(getTileSetIndex());
        return copyTo;
    }
}

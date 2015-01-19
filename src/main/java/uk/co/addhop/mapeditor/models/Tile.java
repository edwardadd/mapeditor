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

    public Tile createCopy() {
        final Tile ret = new Tile(xPosition, yPosition);
        ret.setTileSheet(tileSheet);
        ret.setTileSetIndex(index);
        return ret;
    }

    public void copy(final Tile copy) {
        xPosition = copy.xPosition;
        yPosition = copy.yPosition;
        tileSheet = copy.tileSheet;
        index = copy.index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        if (index != tile.index) return false;
        if (xPosition != tile.xPosition) return false;
        if (yPosition != tile.yPosition) return false;
        if (tileSheet != null ? !tileSheet.equals(tile.tileSheet) : tile.tileSheet != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tileSheet != null ? tileSheet.hashCode() : 0;
        result = 31 * result + index;
        result = 31 * result + xPosition;
        result = 31 * result + yPosition;
        return result;
    }
}

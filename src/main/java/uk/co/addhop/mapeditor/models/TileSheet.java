package uk.co.addhop.mapeditor.models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TileSheet
 * <p/>
 * Created by edwardaddley on 06/01/15.
 */
public class TileSheet {
    private final String filename;
    private final List<Cell> cellList = new ArrayList<Cell>();

    private Image image;

    public TileSheet(final String filename) {
        this.filename = filename;
    }

    public class Cell {
        private Rectangle frame;

        public Cell(final Rectangle rectangle) {
            frame = rectangle;
        }

        public Rectangle getFrame() {
            return frame;
        }
    }

    public String getFilename() {
        return filename;
    }

    public List<Cell> getCellList() {
        return cellList;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void addCell(final int x, final int y, final int width, final int height) {
        cellList.add(new Cell(new Rectangle(x, y, width, height)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TileSheet tileSheet = (TileSheet) o;

        if (!filename.equals(tileSheet.filename)) return false;
        if (image != null ? !image.equals(tileSheet.image) : tileSheet.image != null) return false;
        if (cellList != null ? !cellList.equals(tileSheet.cellList) : tileSheet.cellList != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = filename.hashCode();
        result = 31 * result + (cellList != null ? cellList.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }
}

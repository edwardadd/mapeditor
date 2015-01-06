package uk.co.addhop.mapeditor;

import java.awt.*;
import java.util.List;

/**
 * Created by edwardaddley on 06/01/15.
 */
public class TileSheet {
    private String filename;
    private List<Cell> sheet;
    private Image image;

    public class Cell {
        private Rectangle frame;
        private Image cachedImage; // Take from the main image

        public Rectangle getFrame() {
            return frame;
        }

        public void setFrame(Rectangle frame) {
            this.frame = frame;
        }

        public Image getCachedImage() {
            return cachedImage;
        }

        public void setCachedImage(Image cachedImage) {
            this.cachedImage = cachedImage;
        }
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<Cell> getSheet() {
        return sheet;
    }

    public void setSheet(List<Cell> sheet) {
        this.sheet = sheet;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TileSheet tileSheet = (TileSheet) o;

        if (!filename.equals(tileSheet.filename)) return false;
        if (image != null ? !image.equals(tileSheet.image) : tileSheet.image != null) return false;
        if (sheet != null ? !sheet.equals(tileSheet.sheet) : tileSheet.sheet != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = filename.hashCode();
        result = 31 * result + (sheet != null ? sheet.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }
}

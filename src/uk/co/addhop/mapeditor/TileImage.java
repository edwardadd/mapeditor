package uk.co.addhop.mapeditor;

import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * TileImage is used to do specific graphical functions to the image tiles.
 *
 * @author Edward Addley
 * @version 07 Dec 06
 */
public class TileImage {
    private String filename;
    private Image image;
    private int index;

    public TileImage(Image img) {
        image = img;
        index = 0;
        filename = "";
    }

    public TileImage(int index, String filename) {
        this.index = index;
        this.filename = filename;
        image = new ImageIcon(filename).getImage();
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image img) {
        image = img;
    }

    public void setImage(String filename) {
        this.filename = filename;
        image = new ImageIcon(filename).getImage();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

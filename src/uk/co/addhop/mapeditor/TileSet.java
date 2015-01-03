package uk.co.addhop.mapeditor;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class TileSet  {
   
    /* The image of the tileset */
    private Image tileSetImage;

    /* Width and height of the tiles */
    private int tileWidth;
    private int tileHeight;
    
    public TileSet() {
        tileSetImage = null;
        tileWidth = tileHeight = -1;
    }
    
    public TileSet(String filename, int width, int height) {
        tileSetImage = null;
        
        loadImage(filename);
        
        tileWidth = width;
        tileHeight = height;
    }
    
    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public Image getTileSetImage() {
        if(tileSetImage != null)
            return tileSetImage;
        return null;
    }
    
    public void loadImage(String filename) {
        java.net.URL imageURL = this.getClass().getClassLoader().getResource(filename);
        
        if (imageURL != null) {
            tileSetImage = new ImageIcon(imageURL).getImage();
        } else { // file is not a inside the jar file
            //System.err.println("Couldn't find file: " + imageURL.toString());
            tileSetImage = null;
            tileSetImage = new ImageIcon(filename).getImage();
        }
    }
	
}

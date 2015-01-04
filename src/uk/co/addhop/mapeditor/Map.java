package uk.co.addhop.mapeditor;

import java.util.*;
import java.awt.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;

/**
 * Write a description of class Map here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Map {
    public static final int TILE_WIDTH = 50;
    public static final int TILE_HEIGHT = 50;

    private int width;
    private int height;

    private int screenx = 0;
    private int screeny = 0;
    private String mapFilename;

    private String name;
    private String description;


    private ArrayList<Tile> tileMap;

    private TileImageController tileImageController;

    public Map(TileImageController tileImageController, int width, int height) {
        this.width = width;
        this.height = height;

        screeny = (height * 25) + 50;

        tileMap = new ArrayList<Tile>(width * height);

        this.tileImageController = tileImageController;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tileMap.add(new Tile(x, y, tileImageController.getSelectedTile()));
            }
        }
    }

    public void draw(Graphics g) {

        Iterator i;
        i = tileMap.iterator();
        while (i.hasNext()) {
            Tile tile = (Tile) i.next();
            g.drawImage(tile.getTileImage().getImage(), (tile.getY() * 50) + (tile.getX() * 50) + getScreenX(), (tile.getY() * -25) + (tile.getX() * 25) + getScreenY(), null);
        }
    }

    public int getScreenX() {
        return screenx;
    }

    public int getScreenY() {
        return screeny;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void changeTile(int x, int y) {
        Tile t = (Tile) tileMap.get(y * width + x);
        t.setTileImage(tileImageController.getSelectedTile());
    }

    public void loadMap(String filename) {
        try {
            FileChannel fileChannel = new FileInputStream(filename).getChannel();

            ByteBuffer bBuffer = ByteBuffer.allocate(8);
            bBuffer.order(ByteOrder.LITTLE_ENDIAN);

            fileChannel.read(bBuffer);

            bBuffer.rewind();

            //get the name
            width = bBuffer.getInt();
            height = bBuffer.getInt();

            fileChannel.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void saveMap(String filename) {
        try {
            FileChannel fileChannel = new FileOutputStream(filename).getChannel();

            ByteBuffer bBuffer = ByteBuffer.allocate(8);
            bBuffer.order(ByteOrder.LITTLE_ENDIAN);

            //fileChannel.read(bBuffer);

            //put the name
            //the description
            //width
            bBuffer.putInt(width);
            //height
            bBuffer.putInt(height);
            //number of tiles in the tileset
            //the tile index
            //the tile filename

            //the map
            Iterator i;
            i = tileMap.iterator();
            while (i.hasNext()) {
                Tile tile = (Tile) i.next();
                TileImage tileImage = tile.getTileImage();
                bBuffer.putInt(tileImage.getIndex());
            }

            bBuffer.flip();

            fileChannel.write(bBuffer);

            fileChannel.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

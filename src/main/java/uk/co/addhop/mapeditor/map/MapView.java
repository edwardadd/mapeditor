package uk.co.addhop.mapeditor.map;

import uk.co.addhop.mapeditor.interfaces.Controller;
import uk.co.addhop.mapeditor.interfaces.View;
import uk.co.addhop.mapeditor.models.Map;
import uk.co.addhop.mapeditor.models.Tile;
import uk.co.addhop.mapeditor.models.TileSheet;
import uk.co.addhop.mapeditor.models.TileTypeDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Write a description of class TileImageGUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MapView extends JPanel implements View<MapViewController> {
    private MapViewController controller;

    private java.util.List<Tile> tileList;

    private TileTypeDatabase database;
    private int tileWidth;
    private int tileHeight;

    public MapView() {
        tileList = new ArrayList<Tile>();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

//        g.setColor(Color.RED);
//        g.fillRect(getX(), getY(), getWidth(), getHeight());

        for (Tile tile : tileList) {
            int x = tileWidth * tile.getXPosition();
            int y = tileHeight * tile.getYPosition();

            final TileSheet tileSheet = database.getTileSheet(tile.getTileSheet());
            final TileSheet.Cell cell = tileSheet.getCellList().get(tile.getTileSetIndex());

            int dx = (int) cell.getFrame().getX();
            int dy = (int) cell.getFrame().getY();
            int dx2 = (int) (cell.getFrame().getX() + cell.getFrame().getWidth());
            int dy2 = (int) (cell.getFrame().getY() + cell.getFrame().getHeight());

            g.drawImage(tileSheet.getImage(), x, y, x + tileWidth, y + tileHeight, dx, dy, dx2, dy2, null);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null) {
            final Map map = (Map) arg;
            tileList = map.getMapTiles();
            tileWidth = map.getTileWidth();
            tileHeight = map.getTileHeight();

            setPreferredSize(new Dimension(tileWidth * map.getMapWidth(), tileHeight * map.getMapHeight()));

            if (getMouseListeners().length == 0) {
                addMouseListener(mouseListener);
            }

            revalidate();

            repaint();
        }
    }

    private MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            final int x = Math.floorDiv(e.getX(), tileWidth);
            final int y = Math.floorDiv(e.getY(), tileHeight);

            controller.selectedTile(x, y);

            repaint();

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    };

    @Override
    public Component getView() {
        return this;
    }

    @Override
    public void setController(MapViewController controller) {
        this.controller = controller;
    }

    @Override
    public Controller getController() {
        return controller;
    }

    public void setDatabase(final TileTypeDatabase database) {
        this.database = database;
    }
}

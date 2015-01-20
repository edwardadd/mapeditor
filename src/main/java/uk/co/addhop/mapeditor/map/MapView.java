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
public class MapView extends JPanel implements View<MapView, MapViewController> {
    private MapViewController controller;

    private java.util.List<Tile> tileList;

    private TileTypeDatabase database;
    private int tileWidth;
    private int tileHeight;

    private int mapWidth;
    private int mapHeight;

    public MapView() {
        tileList = new ArrayList<Tile>();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

//        g.setColor(Color.RED);
//        g.fillRect(getX(), getY(), getWidth(), getHeight());

        final int startX = g.getClipBounds().x / tileWidth;
        final int startY = g.getClipBounds().y / tileHeight;
        final int endX = Math.min(1 + (g.getClipBounds().x + g.getClipBounds().width) / tileWidth, mapWidth);
        final int endY = Math.min(1 + (g.getClipBounds().y + g.getClipBounds().height) / tileHeight, mapHeight);

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                final int index = x + y * mapWidth;

                final Tile tile = tileList.get(index);
                final TileSheet tileSheet = database.getTileSheet(tile.getTileSheet());

                if (tileSheet != null) {
                    final TileSheet.Cell cell = tileSheet.getCellList().get(tile.getTileSetIndex());

                    int dx = (int) cell.getFrame().getX();
                    int dy = (int) cell.getFrame().getY();
                    int dx2 = (int) (cell.getFrame().getX() + cell.getFrame().getWidth());
                    int dy2 = (int) (cell.getFrame().getY() + cell.getFrame().getHeight());

                    g.drawImage(tileSheet.getImage(), x * tileWidth, y * tileHeight, x * tileWidth + tileWidth, y * tileHeight + tileHeight, dx, dy, dx2, dy2, null);
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null) {
            final Map map = (Map) arg;
            tileList = map.getMapTiles();
            tileWidth = map.getTileWidth();
            tileHeight = map.getTileHeight();
            mapWidth = map.getMapWidth();
            mapHeight = map.getMapHeight();

            setPreferredSize(new Dimension(tileWidth * mapWidth, tileHeight * mapHeight));

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

            if (x >= 0 && y >= 0 && x < mapWidth && y < mapHeight) {
                controller.selectedTile(x, y);
                repaint();
            }

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
    public MapView getView() {
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

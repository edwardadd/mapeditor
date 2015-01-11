package uk.co.addhop.mapeditor.map;

import uk.co.addhop.mapeditor.models.Map;
import uk.co.addhop.mapeditor.models.Tile;
import uk.co.addhop.mapeditor.models.TileSheet;
import uk.co.addhop.mapeditor.models.TileTypeDatabase;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Write a description of class TileImageGUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MapView extends JPanel implements Observer {
    private JPopupMenu popupmenu;
    private JMenuItem popupadd;
    private JMenuItem popupdel;

    private JFrame window;

    private MapViewController controller;

    private java.util.List<Tile> tileList;

    private TileTypeDatabase database;
    private int tileWidth;
    private int tileHeight;

    public MapView() {
        setBackground(Color.white);

        tileList = new ArrayList<Tile>();
    }

    public void initialize(JFrame win, MapViewController controller, TileTypeDatabase database) {
        this.controller = controller;
        this.database = database;

        this.addMouseListener(controller);

        popupmenu = new JPopupMenu();

        // Create and add a menu item
        popupadd = new JMenuItem("Add Tile Set Image");
        popupadd.addActionListener(controller);

        popupdel = new JMenuItem("Remove Tile Set Image");
        popupdel.addActionListener(controller);

        popupmenu.add(popupadd);
        popupmenu.add(popupdel);

        window = win;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.fillRect(getX(), getY(), getWidth(), getHeight());

        for (Tile tile : tileList) {
            int x = tileWidth * tile.getXPosition();
            int y = tileHeight * tile.getYPosition();

            final TileSheet tileSheet = database.getTileSheet(tile.getTileSheet());
            final TileSheet.Cell cell = tileSheet.getSheet().get(tile.getTileSetIndex());

            int dx = (int) cell.getFrame().getX();
            int dy = (int) cell.getFrame().getY();
            int dx2 = (int) (cell.getFrame().getX() + cell.getFrame().getWidth());
            int dy2 = (int) (cell.getFrame().getY() + cell.getFrame().getHeight());

            g.drawImage(tileSheet.getImage(), x, y, x + tileWidth, y + tileHeight, dx, dy, dx2, dy2, null);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null) {
            Map.NotifyData notifyData = (Map.NotifyData) arg;
            tileList = notifyData.tileList;
            tileWidth = notifyData.width;
            tileHeight = notifyData.height;

//            final Tile lastTile = tileList.get(tileList.size()-1);
//            setSize(tileWidth * lastTile.getXPosition(), tileHeight * lastTile.getYPosition());

            repaint();
        }
    }
}

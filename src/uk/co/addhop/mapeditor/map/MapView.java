package uk.co.addhop.mapeditor.map;

import uk.co.addhop.mapeditor.Tile;
import uk.co.addhop.mapeditor.TileTypeDatabase;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

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

    private java.util.List<Tile> tileSetImages;

    private TileTypeDatabase database;

    public MapView() {
        setBackground(Color.white);

        tileSetImages = new ArrayList<Tile>();
    }

    public void initialize(JFrame win, MapViewController controller) {
        this.controller = controller;

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

        Iterator iter;
        iter = tileSetImages.iterator();

        int i = 0;
        while (iter.hasNext()) {
            Tile tileImg = (Tile) iter.next();
            
            /*g.drawImage( tileImg.getImage(), 0, i * 50, null);
            if(tileImg == controller.getSelectedTile())
            {
                g.draw3DRect(0, i*50, 100, 50, true);
            }*/
            i++;
        }
    }


    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null) {
            tileSetImages = (List<Tile>) arg;
        }
    }
}

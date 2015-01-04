package uk.co.addhop.mapeditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Write a description of class TileImageGUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TileImageGUI extends JPanel implements ActionListener, MouseListener, TileImageView {
    JPopupMenu popupmenu;
    JMenuItem popupadd;
    JMenuItem popupdel;

    JFrame window;

    TileImageController controller;

    ArrayList tileSetImages;

    public TileImageGUI() {

        this.setBackground(Color.white);

        this.addMouseListener(this);
    }

    public void initialize(JFrame win, TileImageController controller) {
        this.controller = controller;

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

    public void updateTileSet(ArrayList list) {
        tileSetImages = list;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Iterator iter;
        iter = tileSetImages.iterator();

        int i = 0;
        while (iter.hasNext()) {
            TileImage tileImg = (TileImage) iter.next();         
            
            /*g.drawImage( tileImg.getImage(), 0, i * 50, null);
            if(tileImg == controller.getSelectedTile())
            {
                g.draw3DRect(0, i*50, 100, 50, true);
            }*/
            i++;
        }
    }

    public void mouseClicked(MouseEvent e) {

        double y = e.getY();
        y = Math.floor(y / 50);

        controller.setSelectedTile((int) y);
        repaint();
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popupmenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popupmenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == popupadd) {
            FileDialog fd = new FileDialog(window);
            fd.setVisible(true);
            String filename = fd.getDirectory() + fd.getFile();

            //controller.addTile(filename);
            repaint();
        }

        if (e.getSource() == popupdel) {

            //tileset.removeTile(tileset.getSelectedTile());
            //controller.removeTile(controller.getSelectedTileIndex());
            repaint();
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }
}

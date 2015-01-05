package uk.co.addhop.mapeditor.map;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Write a description of interface MapViewController here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class MapViewController implements ActionListener, MouseListener {

    private MapView view;
    private MapModel model;

    private int selectedTile;

    public void mouseClicked(MouseEvent e) {

        double y = e.getY();
        y = Math.floor(y / 50);

        selectedTile = (int) y;
        view.invalidate();
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
//        if (e.isPopupTrigger()) {
//            view.showPopup(e.getComponent(), e.getX(), e.getY());
//        }
    }

    public void mouseReleased(MouseEvent e) {
//        if (e.isPopupTrigger()) {
//            view.showPopup(e.getComponent(), e.getX(), e.getY());
//        }
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void actionPerformed(ActionEvent e) {

//        if (e.getSource() == popupadd) {
//            FileDialog fd = new FileDialog(window);
//            fd.setVisible(true);
//            String filename = fd.getDirectory() + fd.getFile();
//
//            //controller.addTile(filename);
//            view.invalidate();
//        }
//
//        if (e.getSource() == popupdel) {
//
//            //tileset.removeTile(tileset.getSelectedTile());
//            //controller.removeTile(controller.getSelectedTileIndex());
//            view.invalidate();
//        }
    }


    public void setViewModel(final MapView tileImageView, final MapModel mapModel) {
        model = mapModel;
        view = tileImageView;
    }

    public int getSelectedTile() {
        return selectedTile;
    }
}

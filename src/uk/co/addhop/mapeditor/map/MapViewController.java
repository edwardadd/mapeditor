package uk.co.addhop.mapeditor.map;

import uk.co.addhop.mapeditor.models.Brush;
import uk.co.addhop.mapeditor.models.Map;
import uk.co.addhop.mapeditor.models.Tile;

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
    private Map model;
    private Brush brush;

    private int selectedTileX;
    private int selectedTileY;

    public void mouseClicked(MouseEvent e) {

        int x = Math.floorDiv(e.getX(), model.getTileWidth());
        int y = Math.floorDiv(e.getY(), model.getTileHeight());

        selectedTileX = x;
        selectedTileY = y;

        Tile tile = model.getTile(x, y);
        brush.setSelectedTile(tile);

        tile.setTileSheet(brush.getTileSheetName());
        tile.setTileSetIndex(brush.getTileSheetCellIndex());
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


    public void setViewModel(final MapView tileImageView, final Map mapModel, final Brush brush) {
        model = mapModel;
        view = tileImageView;
        this.brush = brush;
    }
}

package uk.co.addhop.mapeditor.map;

import uk.co.addhop.mapeditor.interfaces.Controller;
import uk.co.addhop.mapeditor.models.Brush;
import uk.co.addhop.mapeditor.models.Map;
import uk.co.addhop.mapeditor.models.Tile;

/**
 * Write a description of interface MapViewController here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class MapViewController implements Controller<Map> {

    private Map model;
    private Brush brush;

    public void selectedTile(final int x, final int y) {
        final Tile tile = model.getTile(x, y);

        if (tile != null) {
            brush.setSelectedTile(tile);

            tile.setTileSheet(brush.getTileSheetName());
            tile.setTileSetIndex(brush.getTileSheetCellIndex());
        }
    }

    public void setBrush(final Brush brush) {
        this.brush = brush;
    }

    @Override
    public void setModel(Map model) {
        this.model = model;
    }
}

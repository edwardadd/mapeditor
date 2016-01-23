package uk.co.addhop.mapeditor.map;

import uk.co.addhop.mapeditor.command.FillCommand;
import uk.co.addhop.mapeditor.command.TileCommand;
import uk.co.addhop.mapeditor.interfaces.Controller;
import uk.co.addhop.mapeditor.interfaces.MapViewInterface;
import uk.co.addhop.mapeditor.models.Brush;
import uk.co.addhop.mapeditor.models.EventList;
import uk.co.addhop.mapeditor.models.Map;
import uk.co.addhop.mapeditor.models.Tile;

/**
 * Write a description of interface MapViewController here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class MapViewController implements Controller<Map>, MapViewInterface {

    private Map model;
    private Brush brush;

    public void selectedTile(final int x, final int y) {
        final Tile tile = model.getTile(x, y);

        if (tile != null) {
            brush.setSelectedTile(tile);

            switch (brush.getBrushType()) {
                case PAINT:
                    final TileCommand paint = new TileCommand(model, tile, brush.getTileSheetName(), brush.getTileSheetCellIndex());
                    paint.execute();

                    EventList.getInstance().push(paint);
                    break;

                case FILL:
                    final FillCommand fill = new FillCommand(model, tile, brush.getTileSheetName(), brush.getTileSheetCellIndex());
                    fill.execute();

                    EventList.getInstance().push(fill);
                    break;
            }
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

package uk.co.addhop.mapeditor.palette;

import uk.co.addhop.mapeditor.interfaces.Controller;
import uk.co.addhop.mapeditor.models.Brush;

/**
 * PaletteViewController
 * <p/>
 * Created by edwardaddley on 05/01/15.
 */
public class PaletteViewController implements Controller<Brush> {

    private Brush brush;

    @Override
    public void setModel(Brush model) {
        this.brush = model;
    }

    public void changeBrush(final String tileSheetName, final int index) {
        brush.setTileSheetName(tileSheetName);
        brush.setTileSheetCellIndex(index);

        brush.notifyObservers();
    }
}

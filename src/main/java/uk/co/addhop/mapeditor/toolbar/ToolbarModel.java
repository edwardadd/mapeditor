package uk.co.addhop.mapeditor.toolbar;

import uk.co.addhop.mapeditor.models.Brush;

import java.util.Observable;

/**
 * ToolbarModel
 *
 * Created by edwardaddley on 05/01/15.
 */
public class ToolbarModel extends Observable {
    private final Brush brush;

    public ToolbarModel(final Brush brush) {
        this.brush = brush;
    }

    public void changeBrush(final Brush.BrushType brushType) {
        brush.setBrushType(brushType);
        brush.notifyObservers(brush);

        setChanged();

        notifyObservers(brush);
    }
}

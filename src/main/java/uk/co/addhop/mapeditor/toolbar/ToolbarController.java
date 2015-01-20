package uk.co.addhop.mapeditor.toolbar;

import uk.co.addhop.mapeditor.interfaces.Controller;
import uk.co.addhop.mapeditor.models.Brush;
import uk.co.addhop.mapeditor.models.EventList;

/**
 * ToolbarController
 * <p/>
 * Created by edwardaddley on 05/01/15.
 */
public class ToolbarController implements Controller<ToolbarModel> {

    private ToolbarModel toolbarModel;

    public void undo() {
        EventList.getInstance().undo();
    }

    public void redo() {
        EventList.getInstance().redo();
    }

    public void paint() {
        toolbarModel.changeBrush(Brush.BrushType.PAINT);
    }

    public void fill() {
        // Change brush type
        toolbarModel.changeBrush(Brush.BrushType.FILL);
    }

    @Override
    public void setModel(ToolbarModel model) {
        this.toolbarModel = model;
    }
}

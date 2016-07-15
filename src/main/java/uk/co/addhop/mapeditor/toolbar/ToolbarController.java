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

    void undo() {
        EventList.getInstance().undo();
    }

    void redo() {
        EventList.getInstance().redo();
    }

    void paint() {
        toolbarModel.changeBrush(Brush.BrushType.PAINT);
    }

    void fill() {
        // Change brush type
        toolbarModel.changeBrush(Brush.BrushType.FILL);
    }

    public void setModel(ToolbarModel model) {
        this.toolbarModel = model;
    }
}

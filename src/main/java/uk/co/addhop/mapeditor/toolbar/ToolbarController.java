package uk.co.addhop.mapeditor.toolbar;

import uk.co.addhop.mapeditor.interfaces.Controller;
import uk.co.addhop.mapeditor.models.EventList;
import uk.co.addhop.mapeditor.models.Map;

/**
 * ToolbarController
 * <p/>
 * Created by edwardaddley on 05/01/15.
 */
public class ToolbarController implements Controller<ToolbarModel> {

    private ToolbarModel toolbarModel;
    private Map mapModel;

    public ToolbarController(final ToolbarModel toolbarModel, final Map mapModel) {
        this.toolbarModel = toolbarModel;
        this.mapModel = mapModel;
    }

    public void undo() {
        EventList.getInstance().undo();
    }

    public void redo() {
        EventList.getInstance().redo();
    }

    public void fill() {
        // Change brush type
    }

    @Override
    public void setModel(ToolbarModel model) {
        this.toolbarModel = model;
    }
}

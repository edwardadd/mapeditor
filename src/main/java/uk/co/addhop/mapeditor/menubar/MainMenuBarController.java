package uk.co.addhop.mapeditor.menubar;

import uk.co.addhop.mapeditor.interfaces.Controller;
import uk.co.addhop.mapeditor.models.Map;

/**
 * MainMenuBarController
 * <p/>
 * Created by edwardaddley on 17/01/15.
 */
public class MainMenuBarController implements Controller<Map> {
    private Map model;

    @Override
    public void setModel(Map model) {
        this.model = model;
    }

    public void newMap() {
        // Start new dialog and create a new Window
    }

    public void saveMap(final String filename) {
        model.saveTileSet(filename);
    }

    public void loadMap() {
        // Start load dialog and create a new Window
    }
}

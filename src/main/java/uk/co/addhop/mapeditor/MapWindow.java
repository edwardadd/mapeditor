package uk.co.addhop.mapeditor;

import uk.co.addhop.mapeditor.models.Brush;
import uk.co.addhop.mapeditor.models.Map;

import javax.swing.*;

/**
 * Created by edwardaddley on 11/07/15.
 */
public class MapWindow extends JFrame {
    private Map map;
    private Brush brush;

    public MapWindow(final Map map, final Brush brush) {
        super();

        setTitle(map.getMapName() + " - " + map.getFileName());

        this.map = map;
        this.brush = brush;
    }

    public Map getMap() {
        return map;
    }

    public Brush getBrush() {
        return brush;
    }

    @Override
    public void dispose() {
        map.deleteObservers();
        map = null;

        brush.deleteObservers();
        brush = null;
        super.dispose();
    }
}

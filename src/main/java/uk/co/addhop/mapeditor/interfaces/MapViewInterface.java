package uk.co.addhop.mapeditor.interfaces;

import uk.co.addhop.mapeditor.models.Map;

/**
 * Map View Interface for dealing with interactions between the user and the map
 * <p/>
 * Created by edwardaddley on 17/01/16.
 */
public interface MapViewInterface extends Controller<Map> {
    public void selectedTile(final int x, final int y);
}

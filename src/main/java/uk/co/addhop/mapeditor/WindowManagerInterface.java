package uk.co.addhop.mapeditor;

import uk.co.addhop.mapeditor.models.Map;

import java.util.List;

public interface WindowManagerInterface {
    MapWindow createMapWindow(Map mapModel);

    MapWindow getFocusedWindow();

    void setFocusedWindow(MapWindow newFocus);

    void clearFocusedWindow();

    List<MapWindow> getMapWindowList();

    void removeWindow(MapWindow window);
}

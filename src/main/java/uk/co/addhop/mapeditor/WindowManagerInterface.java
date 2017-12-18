package uk.co.addhop.mapeditor;

import java.util.List;

public interface WindowManagerInterface {
    MapWindow getFocusedWindow();

    void setFocusedWindow(MapWindow newFocus);

    void clearFocusedWindow();

    List<MapWindow> getMapWindowList();

    void removeWindow(MapWindow window);
}

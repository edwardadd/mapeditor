package uk.co.addhop.mapeditor.interfaces;

import java.util.Observer;

/**
 * View
 * Created by edwardaddley on 12/01/15.
 */
public interface View<V, C extends Controller<?>> extends Observer {

    V getView();

    void setController(final C controller);

    Controller getController();
}

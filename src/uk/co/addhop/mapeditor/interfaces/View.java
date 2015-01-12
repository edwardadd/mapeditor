package uk.co.addhop.mapeditor.interfaces;

import java.awt.*;
import java.util.Observer;

/**
 * View
 * Created by edwardaddley on 12/01/15.
 */
public interface View<C extends Controller<?>> extends Observer {

    public Component getView();

    public void setController(final C controller);

    public Controller getController();
}

package uk.co.addhop.mapeditor.palette;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * PaletteView
 * <p/>
 * Created by edwardaddley on 05/01/15.
 */
public class PaletteView extends JPanel implements Observer {

    private PaletteViewController controller;

    public PaletteView() {
        super();
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

        g.setColor(Color.BLUE);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void setController(PaletteViewController controller) {
        this.controller = controller;
    }

    public PaletteViewController getController() {
        return controller;
    }

    public Dimension getPreferredSize() {
        return new Dimension(128, 100);
    }
}

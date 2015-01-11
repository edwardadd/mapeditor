package uk.co.addhop.mapeditor.palette;

import uk.co.addhop.mapeditor.models.Brush;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * PaletteViewController
 * <p/>
 * Created by edwardaddley on 05/01/15.
 */
public class PaletteViewController implements ActionListener, MouseListener {

    private Brush brush;

    public PaletteViewController(final Brush brush) {
        this.brush = brush;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        final Point mousePosition = e.getPoint();

        System.out.println("HEY!, You clicked here! " + mousePosition.getX() + ", " + mousePosition.getY());

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

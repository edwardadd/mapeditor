package uk.co.addhop.mapeditor.palette;

import uk.co.addhop.mapeditor.models.TileTypeDatabase;

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
    private TileTypeDatabase database;
    private java.util.List<TileTypeDatabase.DisplayCell> displayCellList;

    private int scrollX = 0;

    public PaletteView() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);

        g.setColor(Color.BLUE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // TODO Optimise, only draw if there is space in the component

        int y = 0;
        if (displayCellList != null) {
            for (TileTypeDatabase.DisplayCell cell : displayCellList) {
                g.drawImage(cell.getImage(),
                        0, y, (int) cell.getFrame().getWidth(), y + (int) cell.getFrame().getHeight(),
                        (int) cell.getFrame().getX(), (int) cell.getFrame().getY(), (int) (cell.getFrame().getX() + cell.getFrame().getWidth()), (int) (cell.getFrame().getY() + cell.getFrame().getHeight()), null);
                y += cell.getFrame().getHeight();
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        displayCellList = database.getListOfDisplayCells();
        repaint();
    }

    public void setDatabase(TileTypeDatabase database) {
        this.database = database;
    }

    public void setController(PaletteViewController controller) {
        this.controller = controller;

        addMouseListener(controller);
    }

    public PaletteViewController getController() {
        return controller;
    }

    public Dimension getPreferredSize() {
        return new Dimension(128, 100);
    }
}

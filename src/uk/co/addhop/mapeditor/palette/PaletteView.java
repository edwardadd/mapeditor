package uk.co.addhop.mapeditor.palette;

import uk.co.addhop.mapeditor.interfaces.Controller;
import uk.co.addhop.mapeditor.interfaces.View;
import uk.co.addhop.mapeditor.models.TileTypeDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

/**
 * PaletteView
 * <p/>
 * Created by edwardaddley on 05/01/15.
 */
public class PaletteView extends JPanel implements MouseListener, ActionListener, View<PaletteViewController> {

    private PaletteViewController controller;
    private TileTypeDatabase database;
    private java.util.List<TileTypeDatabase.DisplayCell> displayCellList;

    public PaletteView() {
        super();

        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);

//        g.setColor(Color.BLUE);
//        g.fillRect(0, 0, getWidth(), getHeight());

        // TODO Optimise, only draw if there is space in the component

        int y = 0;
        if (displayCellList != null) {
            for (TileTypeDatabase.DisplayCell cell : displayCellList) {
                final Image image = database.getTileSheet(cell.getTileSheetName()).getImage();

                g.drawImage(image,
                        0, y, (int) cell.getFrame().getWidth(), y + (int) cell.getFrame().getHeight(),
                        (int) cell.getFrame().getX(), (int) cell.getFrame().getY(), (int) (cell.getFrame().getX() + cell.getFrame().getWidth()), (int) (cell.getFrame().getY() + cell.getFrame().getHeight()), null);
                y += cell.getFrame().getHeight();
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (displayCellList != null) {
            displayCellList.clear();
        }

        displayCellList = database.getListOfDisplayCells();

        int height = 0;
        if (displayCellList != null) {
            for (TileTypeDatabase.DisplayCell cell : displayCellList) {
                height += cell.getFrame().getHeight();
            }
        }

        setPreferredSize(new Dimension(128, height));

        revalidate();
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

        int height = 0;
        if (displayCellList != null) {
            for (TileTypeDatabase.DisplayCell cell : displayCellList) {
                if (mousePosition.getY() >= height && mousePosition.getY() < height + cell.getFrame().getHeight()) {
                    controller.changeBrush(cell.getTileSheetName(), cell.getIndex());
                    break;
                }
                height += cell.getFrame().getHeight();
            }
        }
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

    public void setDatabase(TileTypeDatabase database) {
        this.database = database;
        update(null, null);
    }

    @Override
    public Component getView() {
        return this;
    }

    @Override
    public void setController(PaletteViewController controller) {
        this.controller = controller;
    }

    @Override
    public Controller getController() {
        return this.controller;
    }
}

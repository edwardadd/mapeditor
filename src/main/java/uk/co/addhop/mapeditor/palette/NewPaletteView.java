package uk.co.addhop.mapeditor.palette;

import uk.co.addhop.mapeditor.interfaces.Controller;
import uk.co.addhop.mapeditor.interfaces.View;
import uk.co.addhop.mapeditor.models.TileTypeDatabase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Observable;

/**
 * NewPaletteView
 * <p/>
 * Created by edwardaddley on 12/01/15.
 */
public class NewPaletteView extends JPanel implements View<NewPaletteView, PaletteViewController> {

    private PaletteViewController controller;
    private JList<TileTypeDatabase.DisplayCell> jList;
    private TileTypeDatabase database;
    private java.util.Vector<TileTypeDatabase.DisplayCell> displayCellList;

    public NewPaletteView() {
        jList = new JList<TileTypeDatabase.DisplayCell>();
        jList.setCellRenderer(new TileCellRenderer());
        jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (displayCellList != null && jList.getSelectedIndex() != -1) {
                        final TileTypeDatabase.DisplayCell cell = displayCellList.get(jList.getSelectedIndex());

                        controller.changeBrush(cell.getTileSheetName(), cell.getIndex());
                    }
                }
            }
        });
        add(jList);
    }

    public void setDatabase(final TileTypeDatabase database) {
        this.database = database;
        update(null, null);
    }

    @Override
    public NewPaletteView getView() {
        return this;
    }

    @Override
    public void setController(PaletteViewController controller) {
        this.controller = controller;
    }

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public void update(Observable o, Object arg) {

        if (displayCellList != null) {
            displayCellList.clear();
        }

        displayCellList = database.getListOfDisplayCells();
        jList.setListData(displayCellList);

//        int height = 0;
//        if (displayCellList != null) {
//            for (TileTypeDatabase.DisplayCell cell : displayCellList) {
//                height += cell.getFrame().getHeight();
//            }
//        }
//
//        setPreferredSize(new Dimension(128, height));

        revalidate();
    }

    public class TileCellRenderer extends JPanel implements ListCellRenderer<TileTypeDatabase.DisplayCell> {

        private int GAP = 5;

        private TileTypeDatabase.DisplayCell cell;
        private boolean isSelected;

        public TileCellRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends TileTypeDatabase.DisplayCell> list, TileTypeDatabase.DisplayCell value, int index, boolean isSelected, boolean cellHasFocus) {
            cell = value;

            final Dimension dimension = new Dimension(cell.getFrame().getSize());
            dimension.width += GAP * 2;
            dimension.height += GAP * 2;

            setPreferredSize(dimension);

            this.isSelected = isSelected;
            if (isSelected) {
                setBackground(Color.blue);
            } else {
                setBackground(Color.white);
            }
            return this;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            final Image image = database.getTileSheet(cell.getTileSheetName()).getImage();

            final Rectangle frame = cell.getFrame();
            g.drawImage(image,
                    GAP, GAP, (int) frame.getWidth() + GAP, (int) frame.getHeight() + GAP,
                    (int) frame.getX(), (int) frame.getY(), (int) (frame.getX() + frame.getWidth()), (int) (frame.getY() + frame.getHeight()), null);

            if (isSelected) {
                g.drawRect(0, 0, (int) frame.getWidth() + GAP + GAP - 1, (int) frame.getHeight() + GAP + GAP - 1);
            }
        }
    }
}

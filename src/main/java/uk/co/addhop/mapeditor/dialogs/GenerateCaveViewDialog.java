package uk.co.addhop.mapeditor.dialogs;

import uk.co.addhop.mapeditor.factorys.MapFactory;
import uk.co.addhop.mapeditor.interfaces.MapViewInterface;
import uk.co.addhop.mapeditor.map.MapView;
import uk.co.addhop.mapeditor.models.Map;
import uk.co.addhop.mapeditor.models.TileTypeDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog box for creating random cave systems as a starting point for your map.
 * <p/>
 * Created by edwardaddley on 17/01/16.
 */
public class GenerateCaveViewDialog extends JDialog implements MapViewInterface, ActionListener {

    private MapView mapView;
    private Map map;
    private TileTypeDatabase database;

    public GenerateCaveViewDialog(Frame owner, String title, boolean modal, TileTypeDatabase database) {
        super(owner, title, modal);

        this.database = database;

        makeViews();

        map = MapFactory.generateCave(100, 100);
        mapView.update(null, map);
    }

    private void makeViews() {
        setSize(640, 480);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        mapView = new MapView();
        mapView.setDatabase(database);
        mapView.setController(this);

        final JScrollPane centerScroll = new JScrollPane(mapView, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(centerScroll, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        JButton cancelButton = new JButton("Cancel");
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(okButton);
        bottomPanel.add(cancelButton);

        add(bottomPanel, BorderLayout.PAGE_END);


        okButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }


    @Override
    public void selectedTile(int x, int y) {

        // Generate Cave System
        map = MapFactory.generateCave(100, 100);
        mapView.update(null, map);
    }

    @Override
    public void setModel(Map model) {

    }

    public Map getMap() {
        return map;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand().equals("OK")) {
//            parent.createMapWindow(map);
//        }

        dispose();
    }
}

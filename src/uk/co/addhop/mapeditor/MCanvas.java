package uk.co.addhop.mapeditor;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MCanvas extends JPanel implements MouseMotionListener, MouseListener {
    JFrame window;
    JScrollPane westScroll;
    JScrollPane centerScroll;

    JMenuItem menuItemNew = new JMenuItem("New...");
    JMenuItem menuItemOpen = new JMenuItem("Open");
    JMenuItem menuItemSave = new JMenuItem("Save");
    JMenuItem menuItemSaveAs = new JMenuItem("Save As");
    JMenuItem menuItemQuit = new JMenuItem("Quit");

    JMenuItem menuItemNewTileSet = new JMenuItem("New TileSet...");
    JMenuItem menuItemOpenTileSet = new JMenuItem("Open TileSet");
    JMenuItem menuItemSaveTileSet = new JMenuItem("Save TileSet");
    JMenuItem menuItemSaveAsTileSet = new JMenuItem("Save As TileSet");

    Map map;

    TileImageGUI tileImageGUI;
    TileImageModel tileImageModel;

    boolean isPressed = false;

    public MCanvas() {
        tileImageGUI = new TileImageGUI();
        tileImageModel = new TileImageModel();


        this.setBackground(Color.white);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        tileImageModel.initialize(tileImageGUI);
        map = new Map(tileImageModel, 10, 10);
        makeFrame();
        tileImageGUI.initialize(window, tileImageModel);
    }

    public void makeFrame() {
        window = new JFrame("im MapEditor v3");

        window.setJMenuBar(makeMenu());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setSize(640, 480);
        window.getContentPane().setLayout(new BorderLayout());

        westScroll = new JScrollPane(tileImageGUI);
        centerScroll = new JScrollPane(this);

        window.getContentPane().add(westScroll, BorderLayout.WEST);
        window.getContentPane().add(centerScroll, BorderLayout.CENTER);
        window.pack();

        window.setVisible(true);
    }

    public JMenuBar makeMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        menuBar.add(menuFile);
        menuFile.add(menuItemNew);
        menuFile.add(menuItemOpen);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSaveAs);
        menuFile.addSeparator();
        menuFile.add(menuItemQuit);

        JMenu menuTileSet = new JMenu("TileSet");
        menuBar.add(menuTileSet);
        menuTileSet.add(menuItemNewTileSet);
        menuTileSet.add(menuItemOpenTileSet);
        menuTileSet.add(menuItemSaveTileSet);
        menuTileSet.add(menuItemSaveAsTileSet);

        return menuBar;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        map.draw(g);
    }

    public Dimension getPreferredSize() {
        int newW = map.getWidth() * map.TILE_WIDTH;
        int newH = (map.getHeight() + 2) * map.TILE_HEIGHT;
        return new Dimension(newW, newH);
    }

    public void mouseClicked(MouseEvent e) {

        int x, y;
        y = (int) Math.floor(e.getY() / 50);
        x = (int) Math.floor(e.getX() / 50);

        map.changeTile(x, y);
    }

    public void mouseDragged(MouseEvent e) {

        int x, y;
        y = (int) Math.floor(e.getY() / 50);
        x = (int) Math.floor(e.getX() / 50);

        map.changeTile(x, y);
        repaint();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        repaint();
    }
}

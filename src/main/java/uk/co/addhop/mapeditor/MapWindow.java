package uk.co.addhop.mapeditor;

import uk.co.addhop.mapeditor.map.MapView;
import uk.co.addhop.mapeditor.models.Brush;
import uk.co.addhop.mapeditor.models.Map;
import uk.co.addhop.mapeditor.palette.NewPaletteView;
import uk.co.addhop.mapeditor.toolbar.ToolbarView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by edwardaddley on 11/07/15.
 */
public class MapWindow extends JFrame {
    private Map map;
    private Brush brush;
    private MainApplication windows;

    public MapWindow(final Map map, final Brush brush) {
        super();

        setTitle(map.getMapName() + " - " + map.getFileName());

        this.map = map;
        this.brush = brush;
    }

    public Map getMap() {
        return map;
    }

    public Brush getBrush() {
        return brush;
    }

    @Override
    public void dispose() {
        map.deleteObservers();
        map = null;

        brush.deleteObservers();
        brush = null;
        super.dispose();
    }

    public void setup(MapView mapView, ToolbarView toolbarView, NewPaletteView paletteView) {
        // Set up split
        this.getContentPane().setLayout(new BorderLayout());

        final JScrollPane westScroll = new JScrollPane(paletteView, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        final JScrollPane centerScroll = new JScrollPane(mapView, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.getContentPane().add(toolbarView, BorderLayout.PAGE_START);
        this.getContentPane().add(westScroll, BorderLayout.WEST);
        this.getContentPane().add(centerScroll, BorderLayout.CENTER);
        this.getContentPane().add(new JPanel(), BorderLayout.SOUTH); // Space filler

        this.setSize(1024, 800);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {
                System.out.println("windowOpened - " + MapWindow.this.getTitle());
            }

            public void windowClosing(WindowEvent e) {
                System.out.println("windowClosing - " + MapWindow.this.getTitle());

                final String filename = map.getFileName();
                if (filename == null) {
                    final JDialog dialog = new JDialog(MapWindow.this, "Save before quitting?", true);

                    final JPanel panel = new JPanel(new BorderLayout());
                    final JLabel label = new JLabel("Do you wish to save " + map.getMapName() + " map before quitting?");
                    label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    panel.add(label, BorderLayout.CENTER);

                    final JPanel buttonPanel = new JPanel();
                    final JButton okButton = (JButton) buttonPanel.add(new JButton("OK"));
                    final JButton cancelButton = (JButton) buttonPanel.add(new JButton("Cancel"));
                    panel.add(buttonPanel, BorderLayout.PAGE_END);

                    dialog.getContentPane().add(panel);

                    okButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            final JFileChooser chooser = new JFileChooser();
                            final int returnVal = chooser.showSaveDialog(null);

                            if (returnVal == JFileChooser.APPROVE_OPTION) {
                                //System.out.println("You chose to open this file: " +
                                final String filePath = chooser.getSelectedFile().getAbsolutePath();
                                map.saveTileSet(filePath);

                                // Add to most recent file list
                            }

                            dialog.dispose();
                        }
                    });

                    cancelButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dialog.dispose();
                        }
                    });

                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                } else {
                    map.saveTileSet(map.getFileName());
                }
            }

            public void windowClosed(WindowEvent e) {
                System.out.println("windowClosed - " + MapWindow.this.getTitle());

                windows.removeWindow(MapWindow.this);

                if (windows.getFocusedWindow() == MapWindow.this) {
                    windows.clearFocusedWindow();
                }
            }

            public void windowIconified(WindowEvent e) {
                System.out.println("windowIconified - " + MapWindow.this.getTitle());
            }

            public void windowDeiconified(WindowEvent e) {
                System.out.println("windowDeiconified - " + MapWindow.this.getTitle());
            }

            public void windowActivated(WindowEvent e) {
                System.out.println("windowActivated - " + MapWindow.this.getTitle());

                windows.setFocusedWindow(MapWindow.this);
            }

            public void windowDeactivated(WindowEvent e) {
                System.out.println("windowDeactivated - " + MapWindow.this.getTitle());
            }
        });
    }
}

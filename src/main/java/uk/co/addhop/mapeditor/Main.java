package uk.co.addhop.mapeditor;

import java.awt.*;

public class Main {

    private static MainApplication application;

    public static void main(String[] args) {
        // Note: Swing UI changes must be performed in the dispatch event thread.
        EventQueue.invokeLater(() -> {
            application = new MainApplication();
            application.init();
        });
    }
}

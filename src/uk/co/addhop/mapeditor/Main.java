package uk.co.addhop.mapeditor;

import java.awt.*;

public class Main {

    private static MainApplication application;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                application = new MainApplication();
                application.init();
            }
        });
    }

    public MainApplication getApplication()
    {
        return application;
    }
}

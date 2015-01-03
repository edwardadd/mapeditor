package uk.co.addhop.mapeditor;

public class Main {

    private static MainApplication application;

    public static void main(String[] args) {
        application = new MainApplication();
        application.init();
    }

    public MainApplication getApplication()
    {
        return application;
    }
}

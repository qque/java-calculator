package calculator;

public class Settings {
    private static Settings instance;

    public Settings() {};

    public Settings getInstance() {
        if (instance == null) instance = new Settings();
        return instance;
    }

    // setting values
    public static int consoleFontSize = 47;

}

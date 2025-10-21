package calculator;

import java.awt.Font;

public class Settings {
    private static Settings instance;

    public Settings() {};

    public Settings getInstance() {
        if (instance == null) instance = new Settings();
        return instance;
    }

        // setting w/ default values

    // font settings
    public static String consoleFontName = "Consolas";
    public static int consoleFontStyle = Font.PLAIN;
    public static int consoleFontSize = 47;


    // program behavior
    public static boolean DEBUG_MODE = true;
    public static boolean sendDebugConsoleInputToHistory = true;
    public static boolean displayOutputOnConsoleDebug = true;

}

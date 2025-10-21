package calculator;

import java.awt.Font;

public class Settings {

    // font settings
    public static String consoleFontName = "Consolas";
    public static int consoleFontStyle = Font.PLAIN;
    public static int consoleFontSize = 47;

    public static String buttonFontName = "SansSerif";
    public static int buttonFontStyle = Font.BOLD;
    public static int buttonFontSize = 18;


    // program behavior
    public static boolean DEBUG_MODE = true;
    public static boolean DEBUG_LOG = false;

    public static boolean sendDebugConsoleInputToHistory = true;
    public static boolean displayOutputOnConsoleDebug = true;


    // check and correct for inconsistencies in the setting values
    // depending on what the issue is, it
    public static void ensureConsistent() {

    }

}

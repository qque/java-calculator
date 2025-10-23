package calculator;

import java.awt.Font;

public class Settings {

    /* Fonts & Display Settings */
    public static String consoleFontName = "Consolas";
    public static int consoleFontStyle = Font.PLAIN;
    public static int consoleFontSize = 47;

    public static String buttonFontName = "SansSerif";
    public static int buttonFontStyle = Font.BOLD;
    public static int buttonFontSize = 18;

    public static String popupButtonFontName = "SansSerif";
    public static int popupButtonFontStyle = Font.BOLD;
    public static int popupButtonFontSize = 9;


    /* Program Behavior 
            NOTE: These cannot be directly modified while the application is open;
            if they attempt to, it will prompt them to reload (see SettingsFrame.java)

            To set these without having to reload, pass them to run.bat through the command line
    */
    public static boolean DEBUG_MODE = true;
    public static boolean DEBUG_LOG = true; // logs debug output to ../../resources/logs/, 1 file per session

    public static boolean ADD_CUSTOM_FUNCTION_FILE = false;
    public static boolean USE_CUSTOM_FUNCTION_FILE = false; // if both are set, use is prioritized over add
    public static String CUSTOM_FUNCTION_FILE = null;

    public static boolean LOAD_ADVANCED = true; // if 

    public static boolean sendDebugConsoleInputToHistory = true;
    public static boolean displayOutputOnConsoleDebug = true;

}

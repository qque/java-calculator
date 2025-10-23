package calculator;

import java.util.Map;

public class Settings {

    private static Map<String, Object> settings = Map.of(
        "", ""
    );

    /* Fonts & Display Settings */
    public static final String consoleFontName = "Consolas";
    public static final int consoleFontStyle = 0; // plain
    public static final int consoleFontSize = 47;

    public static final String buttonFontName = "SansSerif";
    public static final int buttonFontStyle = 1; // bold
    public static final int buttonFontSize = 18;

    public static final String popupButtonFontName = "SansSerif";
    public static final int popupButtonFontStyle = 1; // bold
    public static final int popupButtonFontSize = 9;


    /* Program Behavior 
            NOTE: These cannot be directly modified while the application is open;
            if they attempt to, it will prompt them to reload (see SettingsFrame.java)

            To set these without having to reload, pass them to run.bat through the command line
    */
    public static final boolean DEBUG_MODE = true;
    public static final boolean DEBUG_LOG = true; // logs debug output to ../../resources/logs/, 1 file per session
    
    public static final boolean OPEN_DEBUG_CONSOLE_ON_EXECUTION = false; // causes a larger version of the debug console to be opened on execution instead of the standard calculator frame   
    public static final boolean SEND_DEBUG_CONSOLE_OUT_TO_HIST = true;
    public static final boolean DISPLAY_DEBUG_CONSOLE_OUT = true;

    public static final boolean ADD_CUSTOM_FUNCTION_FILE = false;
    public static final boolean USE_CUSTOM_FUNCTION_FILE = false; // if both are set, use is prioritized over add
    public static final String CUSTOM_FUNCTION_FILE = null;

    public static final boolean LOAD_ADVANCED = true; // if false, most submenu functions will be disabled

}

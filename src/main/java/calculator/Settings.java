/*
 *  Defines settings used throughout the calculator, which can be changed in the settings frame, opened with Ctrl + S.
 *  These are divided into three categories: font/display settings, calculation settings, and core program behavior.
 */

package calculator;

import java.util.Map;

public class Settings {

    private Map<String, Object> settings = Map.ofEntries(
        Map.entry(null, null),
        Map.entry(null, null),
        Map.entry(null, null),
        Map.entry(null, null),
        Map.entry(null, null),
        Map.entry(null, null),
        Map.entry(null, null),
        Map.entry(null, null),
        Map.entry(null, null),
        Map.entry(null, null)
    );

    public Settings(Map<String, Object> settings) {
        this.settings = settings;
    }

    // set
    public Settings() {}

    private static Settings instance;

    public static Settings getSettings() {
        if (instance == null) instance = new Settings();
        return instance;
    }

    // ensures consistency between settings to fix incompatible values (e.g. settings DEBUG_MODE=false but DEBUG_LOG=true)
    // if the inconsistencies are predictable and are easily fixed then they will be corrected without throwing any error
    // if they are fatal, however, (e.g. passing a negative value to consoleFontSize), the program will exit
    private void ensureConsistent() {

    }


    /* Fonts & Display Settings */
    private String consoleFontName = "Consolas";
    private int consoleFontStyle = 0; // plain
    private int consoleFontSize = 47;

    private String buttonFontName = "SansSerif";
    private int buttonFontStyle = 1; // bold
    private int buttonFontSize = 18;

    private String popupButtonFontName = "SansSerif";
    private int popupButtonFontStyle = 1; // bold
    private int popupButtonFontSize = 9;

    // getters/setters
    public String getConsoleFontName() { return consoleFontName; }
    public int getConsoleFontStyle() { return consoleFontStyle; }
    public int getConsoleFontSize() { return consoleFontSize; }
    public void setConsoleFontName(String name) { consoleFontName = name; }
    public void setConsoleFontStyle(int style) { consoleFontStyle = style; }
    public void setConsoleFontSize(int size) { consoleFontSize = size; }

    public String getButtonFontName() { return buttonFontName; }
    public int getButtonFontStyle() { return buttonFontStyle; }
    public int getButtonFontSize() { return buttonFontSize; }
    public void setButtonFontName(String name) { buttonFontName = name; }
    public void setButtonFontStyle(int style) { buttonFontStyle = style; }
    public void setButtonFontSize(int size) { buttonFontSize = size; }

    public String getPopupButtonFontName() { return popupButtonFontName; }
    public int getPopupButtonFontStyle() { return popupButtonFontStyle; }
    public int getPopupButtonFontSize() { return popupButtonFontSize; }
    public void setPopupButtonFontName(String name) { popupButtonFontName = name; }
    public void setPopupButtonFontStyle(int style) { popupButtonFontStyle = style; }
    public void setPopupButtonFontSize(int size) { popupButtonFontSize = size; }



    
    /* Calculation Settings */
    private int precision = -1; // -1 = regular floating point (double) precision, 

    // getters/setters
    // ...



    /* Program Behavior 
     *  
     *     NOTE: These are final, and cannot be directly modified while the application is open;
     *           if the user attempts to, it will prompt them to reload (see SettingsFrame.java)
     *  
     *           To set these without having to reload, pass them to ./run in the command line
     *  
     *           Also, some of the settings here with longer names have abbreviated versions.
     *           This is here because, otherwise, the user would have to write the full name in the console
     *           when setting them when passing them through ./run
     */ 
    private final boolean DEBUG_MODE = true;
    private final boolean DEBUG_LOG = true; // logs debug output to ../../resources/logs/, 1 file per session
    
    private final boolean OPEN_DEBUG_CONSOLE_ON_EXECUTION = false; // causes a larger version of the debug console to be opened on execution instead of the standard calculator frame   
    private final boolean ODCOE = OPEN_DEBUG_CONSOLE_ON_EXECUTION;
    private final boolean SEND_DEBUG_CONSOLE_OUT_TO_HIST = true;
    private final boolean SDCOTH = SEND_DEBUG_CONSOLE_OUT_TO_HIST;
    private final boolean DISPLAY_DEBUG_CONSOLE_OUT = true;
    private final boolean DDCO = DISPLAY_DEBUG_CONSOLE_OUT;

    private final boolean ADD_CUSTOM_FUNCTION_FILE = false;
    private final boolean ACFT = ADD_CUSTOM_FUNCTION_FILE;
    private final boolean USE_CUSTOM_FUNCTION_FILE = false; // if both are set, use is prioritized over add
    private final boolean UCFF = USE_CUSTOM_FUNCTION_FILE;
    private final String CUSTOM_FUNCTION_FILE = null;
    private final String CFF = CUSTOM_FUNCTION_FILE;

    private final boolean LOAD_ADVANCED = true; // if false, most submenu functions will be disabled

    // getters (no setters since final)
    public boolean getDebugMode() { return DEBUG_MODE; }
    public boolean getDebugLog() { return DEBUG_LOG; }
    
    public boolean getOpenDebugConsoleOnExecution() { return ODCOE; }
    public boolean getSendDebugConsoleOutToHist() { return SDCOTH; }
    public boolean getDisplayDebugConsoleOut() { return DDCO; }
    
    public boolean getAddCustomFunctionFile() { return ACFT; }
    public boolean getUseCustomFunctionFile() { return UCFF; }
    public String getCustomFunctionFile() { return CFF; }
    
    public boolean getLoadAdvanced() { return LOAD_ADVANCED; }

}

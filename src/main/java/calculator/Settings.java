/*
 *  Defines settings used throughout the calculator, which can be changed in the settings frame, opened with Ctrl + S.
 *  These are divided into three categories: font/display settings, calculation settings, and core program behavior.
 */

package calculator;

public class Settings {
    
    // note that this only sets the final (program behavior) settings, as the rest have their
    // default values preset and can be directly changed with setters.
    // the final settings, on the other hand, have to be set on construction.
    public Settings(Object[] finalSettings) {
        this.DEBUG_MODE = (boolean) finalSettings[0];
        this.DEBUG_LOG = (boolean) finalSettings[1];
        this.OPEN_DEBUG_CONSOLE_ON_EXECUTION = (boolean) finalSettings[2];
        this.SEND_DEBUG_CONSOLE_OUT_TO_HIST = (boolean) finalSettings[3];
        this.DISPLAY_DEBUG_CONSOLE_OUT = (boolean) finalSettings[4];
        this.ADD_CUSTOM_FUNCTION_FILE  = (boolean) finalSettings[5];
        this.USE_CUSTOM_FUNCTION_FILE = (boolean) finalSettings[6];
        this.CUSTOM_FUNCTION_FILE = (String) finalSettings[7];
        this.LOAD_ADVANCED = (boolean) finalSettings[8];
        ensureConsistent();
    }

    private static Settings instance;

    // this is exclusively used in Main when settings are passed through ./run
    public static void setSettings(Object[] finalSettings) {
        instance = new Settings(finalSettings);
    }

    // sets instance with default settings
    // note that if non-default settings were given in Main, it won't be overwritten, as when this
    // overloaded version is ran in other classes, instance will not be null, so it will just 
    public static Settings getSettings() {
        if (instance == null) instance = new Settings(defaultValues);
        return instance;
    }

    // ensures consistency between settings to fix incompatible values (e.g. settings DEBUG_MODE=false but DEBUG_LOG=true)
    // if the inconsistencies are predictable and are easily fixed then they will be corrected without throwing any error
    // if they are fatal, however, (e.g. passing a negative value to consoleFontSize), the program will exit
    private void ensureConsistent() {
        // ...
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
    public int getPrecision() { return precision; }
    public void setPrecision(int prec) { precision = prec; }



    /* Program Behavior 
     *  
     *     NOTE: These are final, and cannot be directly modified while the application is open;
     *           if the user attempts to, it will prompt them to reload (see SettingsFrame.java)
     *           This reloading will effectively close 
     *  
     *           To set these without having to reload, pass them to ./run in the command line
     *  
     */ 
    private final boolean DEBUG_MODE;
    private final boolean DEBUG_LOG; // logs debug output to ../../resources/logs/, 1 file per session
    
    private final boolean OPEN_DEBUG_CONSOLE_ON_EXECUTION; // causes a larger version of the debug console to be opened on execution instead of the standard calculator frame 
    private final boolean SEND_DEBUG_CONSOLE_OUT_TO_HIST;
    private final boolean DISPLAY_DEBUG_CONSOLE_OUT;

    private final boolean ADD_CUSTOM_FUNCTION_FILE;
    private final boolean USE_CUSTOM_FUNCTION_FILE; // if both are set, use is prioritized over add
    private final String CUSTOM_FUNCTION_FILE;

    private final boolean LOAD_ADVANCED; // if false, most submenu functions will be disabled

    // getters and default values (no setters since final)
    public final static Object[] defaultValues = {
        true,
        true,

        false,
        true,
        true,

        false,
        false,
        null,

        true,
    };

    public boolean getDebugMode() { return DEBUG_MODE; }
    public boolean getDebugLog() { return DEBUG_LOG; }
    
    public boolean getOpenDebugConsoleOnExecution() { return OPEN_DEBUG_CONSOLE_ON_EXECUTION; }
    public boolean getSendDebugConsoleOutToHist() { return SEND_DEBUG_CONSOLE_OUT_TO_HIST; }
    public boolean getDisplayDebugConsoleOut() { return DISPLAY_DEBUG_CONSOLE_OUT; }
    
    public boolean getAddCustomFunctionFile() { return ADD_CUSTOM_FUNCTION_FILE; }
    public boolean getUseCustomFunctionFile() { return USE_CUSTOM_FUNCTION_FILE; }
    public String getCustomFunctionFile() { return CUSTOM_FUNCTION_FILE; }
    
    public boolean getLoadAdvanced() { return LOAD_ADVANCED; }

}

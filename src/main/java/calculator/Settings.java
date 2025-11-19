/*
 *  Defines settings used throughout the calculator, which can be changed in the settings frame, opened with Ctrl + S.
 *  These are divided into three categories: font/display settings, calculation settings, and core program behavior.
 *  
 *  Settings are currently stored in a string array with fixed values.
 *  This is not ideal for maintainability, and should possibly be changed in the future, but it is kept now for convenience.
 */

package calculator;

public class Settings {

    public final static String[] defaultValues = {
        "true",
        "true",

        "false",
        "true",
        "true",

        "false",
        "null",

        "false",
        "false",
        "null",

        "true",

        "true",
    };

    private String[] settingValues;
    
    // note that this only sets the final (program behavior) settings, as the rest have their
    // default values preset and can be directly changed with setters.
    // the final settings, on the other hand, have to be set on construction.
    public Settings(String[] finalSettings) {
        ensureConsistent(finalSettings);

        this.settingValues = finalSettings;
        
        this.DEBUG_MODE = Boolean.parseBoolean(finalSettings[0]);
        this.DEBUG_LOG = Boolean.parseBoolean(finalSettings[1]);

        this.OPEN_DEBUG_CONSOLE_ON_EXECUTION = Boolean.parseBoolean(finalSettings[2]);
        this.SEND_DEBUG_CONSOLE_OUT_TO_HIST = Boolean.parseBoolean(finalSettings[3]);
        this.DISPLAY_DEBUG_CONSOLE_OUT = Boolean.parseBoolean(finalSettings[4]);

        this.RUN_DEBUG_FILE = Boolean.parseBoolean(finalSettings[5]);
        this.CUSTOM_DEBUG_FILE = finalSettings[6];

        this.ADD_CUSTOM_FUNCTION_FILE  = Boolean.parseBoolean(finalSettings[7]);
        this.USE_CUSTOM_FUNCTION_FILE = Boolean.parseBoolean(finalSettings[8]);
        this.CUSTOM_FUNCTION_FILE = finalSettings[9];

        this.LOAD_ADVANCED = Boolean.parseBoolean(finalSettings[10]);

        this.DARK_MODE = Boolean.parseBoolean(finalSettings[11]);

        instance = this;
    }

    // ensures consistency between settings to fix incompatible values (e.g. settings DEBUG_MODE=false but DEBUG_LOG=true)
    // if the inconsistencies are predictable and are easily fixed then they will be corrected without throwing any error
    // if they are fatal, however, (e.g. passing a negative value to consoleFontSize), the program will catch the error and exit
    private static void ensureConsistent(String[] finalSettings) {
        if (finalSettings[6] != "null") {
            finalSettings[5] = "true";
        } else if (finalSettings[6] == "null" && finalSettings[5] == "true") {
            System.out.println(new IllegalArgumentException("Custom debug file must be set").getMessage());
            System.exit(1);
        }

        if (finalSettings[9] != "null" && finalSettings[7] == "false" && finalSettings[8] == "false") {
            finalSettings[7] = "true";
        }
    }

    public String toString() {
        String s = "";
        for (String v : settingValues) {
            s += v + "\n";
        }

        return s;
    }

    private static Settings instance;

    // static way to access settings in other classes
    public static Settings getSettings() {
        return instance;
    }

    /* Fonts & Display Settings */
    private String consoleFontName = "Monospaced";
    private int consoleFontStyle = 0; // plain
    private int consoleFontSize = 47;

    private String buttonFontName = "SansSerif";
    private int buttonFontStyle = 1; // bold
    private int buttonFontSize = 18;

    private String popupButtonFontName = "SansSerif";
    private int popupButtonFontStyle = 1; // bold
    private int popupButtonFontSize = 9;

    // debug font?
    // notepad font?

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
    private int precision = -1; // -1 = don't use custom precision, i.e. use regular double (or `float` in python)
                                // any other positive integer 

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

    private final boolean RUN_DEBUG_FILE; // deprecated
    private final String CUSTOM_DEBUG_FILE; // deprecated

    private final boolean ADD_CUSTOM_FUNCTION_FILE;
    private final boolean USE_CUSTOM_FUNCTION_FILE; // if both are set, use is prioritized over add
    private final String CUSTOM_FUNCTION_FILE; // if neither are set but custom_function_file is, default to add

    private final boolean LOAD_ADVANCED; // if false, most submenu functions will be disabled

    private final boolean DARK_MODE; // dark vs. light theme (default is dark)

    // getters and default values (no setters since final)
    public boolean isDebugMode() { return DEBUG_MODE; }
    public boolean isDebugLog() { return DEBUG_LOG; }
    
    public boolean isOpenDebugConsoleOnExecution() { return OPEN_DEBUG_CONSOLE_ON_EXECUTION; }
    public boolean isSendDebugConsoleOutToHist() { return SEND_DEBUG_CONSOLE_OUT_TO_HIST; }
    public boolean isDisplayDebugConsoleOut() { return DISPLAY_DEBUG_CONSOLE_OUT; }
    
    public boolean isRunDebugFile() { return RUN_DEBUG_FILE; }
    public String getCustomDebugFile() { return CUSTOM_DEBUG_FILE; }

    public boolean isAddCustomFunctionFile() { return ADD_CUSTOM_FUNCTION_FILE; }
    public boolean isUseCustomFunctionFile() { return USE_CUSTOM_FUNCTION_FILE; }
    public String getCustomFunctionFile() { return CUSTOM_FUNCTION_FILE; }
    
    public boolean isLoadAdvanced() { return LOAD_ADVANCED; }

    public boolean isDarkMode() { return DARK_MODE; }

}

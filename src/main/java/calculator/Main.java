package calculator;

import javax.swing.SwingUtilities;

import java.util.Properties;
import java.util.Enumeration;

import calculator.ui.CalculatorFrame;
import calculator.ui.DebugConsole;

public class Main {

    static Runnable main = new Runnable() {
        public void run() {
            new CalculatorFrame();
        } 
    };
    

    public static void main(String[] args) {
        /* Capture arguments to settings */
        Properties properties = System.getProperties();
        Enumeration<?> propertyNames = properties.propertyNames();

        Object[] finalValues = Settings.defaultValues;

        while (propertyNames.hasMoreElements()) {
            String name = ((String) propertyNames.nextElement()).toLowerCase();
            String value = properties.getProperty(name);
            boolean setTrue = (value == "true" || value == "1");
            boolean setFalse = (value == "false" || value == "0");

            if ((name == "debug_mode" || name == "debug") && setFalse) {
                finalValues[0] = false;
            }

            if ((name == "debug_log" || name == "log") && setFalse) {
                finalValues[1] = false;
            }

            if ((name == "odcoe" || name == "open_debug_console_on_execution" || name == "open_debug") && setTrue) {
                finalValues[2] = true;

                main = new Runnable() {
                    public void run() {
                        new DebugConsole();
                    }
                };
            }

            if ((name == "sdcoth" || name == "send_debug_console_out_to_hist" || name == "debug_history") && setFalse) {
                finalValues[3] = false;
            }

            if ((name == "ddco" || name == "display_debug_console_out" || name == "debug_output") && setFalse) {
                finalValues[4] = false;
            }

            if ((name == "acff" || name == "add_custom_function_file" || name == "add_custom") && setTrue) {
                finalValues[5] = true;
            }

            if ((name == "ucff" || name == "use_custom_function_file" || name == "use_custom") && setTrue) {
                finalValues[6] = true;
            }

            if (name == "cff" || name == "custom_function_file" || name == "custom_file") {
                finalValues[7] = value;
            }

            if ((name == "load_advanced" || name == "advanced") && setFalse) {
                finalValues[8] = false;
            }
        }

        Settings.setSettings(finalValues);

        /* Open logger */
        Settings settings = Settings.getSettings();
        
        if (settings.getDebugLog()) {
            Logger logger = Logger.getInstance();
        }


        SwingUtilities.invokeLater(main);
    }

}

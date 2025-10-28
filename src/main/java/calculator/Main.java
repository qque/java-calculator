package calculator;

import javax.swing.SwingUtilities;

import java.io.IOException;

import calculator.logic.DebugFileParse;

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
        Object[] finalValues = Settings.defaultValues;

        // because of the way arguments are captured in ./run, `args[0].split(" ")` actually becomes what we expect args to be
        // e.g. if you ran `./run -f example.txt -Dlog=false`, args[0] would be "-Dcustom_debug_file"  

        args = args[0].split(" ");

        for (int i = 0; i < args.length; i++) { // e.g. -Dlog=false
            String arg = args[i].substring(2); // "log=false"
            String name = arg.split("=")[0]; // "log"
            String value = arg.split("=")[1]; // "false"
            boolean setTrue = (value == "true" || value == "1");
            boolean setFalse = (value == "false" || value == "0");
            System.out.println(name);
            System.out.println(value);

            if ((name == "debug_mode" || name == "debug") && setFalse) {
                finalValues[0] = false;
            }

            if ((name == "debug_log" || name == "log") && setFalse) {
                finalValues[1] = false;
            }

            if ((name == "odcoe" || name == "open_debug_console_on_execution" || name == "open_debug") && setTrue) {
                finalValues[2] = true;
            }

            if ((name == "sdcoth" || name == "send_debug_console_out_to_hist" || name == "debug_history") && setFalse) {
                finalValues[3] = false;
            }

            if ((name == "ddco" || name == "display_debug_console_out" || name == "debug_output") && setFalse) {
                finalValues[4] = false;
            }

            if ((name == "rdf" || name == "run_debug_file") && setTrue) {
                finalValues[5] = true;
                System.out.println("reached");
            }

            if (name == "cdf" || name == "custom_debug_file" || name == "debug_file") {
                finalValues[6] = value;
                System.out.println("reached");
            }

            if ((name == "acff" || name == "add_custom_function_file" || name == "add_custom") && setTrue) {
                finalValues[7] = true;
            }

            if ((name == "ucff" || name == "use_custom_function_file" || name == "use_custom") && setTrue) {
                finalValues[8] = true;
            }

            if (name == "cff" || name == "custom_function_file" || name == "function_file") {
                finalValues[9] = value;
            }

            if ((name == "load_advanced" || name == "advanced") && setFalse) {
                finalValues[10] = false;
            }
        }

        Settings.setSettings(finalValues);

        ;


        /* Use settings for certain initialization behavior */
        Settings settings = Settings.getSettings();

        // open logger & log arguments if applicable
        if (settings.isDebugLog()) {
            Logger logger = Logger.getInstance();
            for (String s : args) {
                logger.log(s);
            }
        }
        // debug arguments as well if applicable
        if (settings.isDebugMode()) {
            for (String s : args) {
                System.out.println("PARSED ARGUMENT: " + s);
            }
        }

        // open debug console if odcoe=1
        // open debug file menu
        if (settings.isOpenDebugConsoleOnExecution()) {
            main = new Runnable() {
                public void run() {
                    new DebugConsole();
                }
            };
        } else if (settings.isRunDebugFile()) {
            try {
                DebugFileParse debugFile = new DebugFileParse(settings.getCustomDebugFile());
                
                main = debugFile.outputRunnable();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        /* Start GUI */
        SwingUtilities.invokeLater(main);
    }

}

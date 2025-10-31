package calculator;

import javax.swing.SwingUtilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import calculator.logic.DebugFileParse;

import calculator.ui.CalculatorFrame;
import calculator.ui.DebugConsole;

public class Main {

    static Runnable main = new Runnable() {
        public void run() {
            new CalculatorFrame();
        } 
    };

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    public static void main(String[] args) {
        /* Capture arguments to settings */
        final int size = Settings.defaultValues.length;
        String[] finalValues = new String[size];

        int len = args.length;

        if (len > 0) {
            try {
                for (int i = 0; i < len; i++) { // e.g. -Dlog=false
                    String[] splitArgs = args[i].substring(2).split("="); // ["log", "false"]
                    String name = splitArgs[0];
                    String value = splitArgs[1];
                    boolean setTrue = value.equals("true") || value.equals("1");
                    boolean setFalse = value.equals("false") || value.equals("0");

                    if ((name.equals("debug_mode") || name.equals("debug")) && setFalse) {
                        finalValues[0] = "false";
                    }

                    if ((name.equals("debug_log") || name.equals("log")) && setFalse) {
                        finalValues[1] = "false";
                    }

                    if ((name.equals("odcoe") || name.equals("open_debug_console_on_execution") || name.equals("open_debug")) && setTrue) {
                        finalValues[2] = "true";
                    }

                    if ((name.equals("sdcoth") || name.equals("send_debug_console_out_to_hist") || name.equals("debug_history")) && setFalse) {
                        finalValues[3] = "false";
                    }

                    if ((name.equals("ddco") || name.equals("display_debug_console_out") || name.equals("debug_output")) && setFalse) {
                        finalValues[4] = "false";
                    }

                    if ((name.equals("rdf") || name.equals("run_debug_file")) && setTrue) {
                        finalValues[5] = "true";
                    }

                    if (name.equals("cdf") || name.equals("custom_debug_file") || name.equals("debug_file")) {
                        finalValues[6] = value;
                    }

                    if ((name.equals("acff") || name.equals("add_custom_function_file") || name.equals("add_custom") && setTrue)) {
                        finalValues[7] = "true";
                    }

                    if ((name.equals("ucff") || name.equals("use_custom_function_file") || name.equals("use_custom")) && setTrue) {
                        finalValues[8] = "true";
                    }

                    if (name.equals("cff") || name.equals("custom_function_file") || name.equals("function_file")) {
                        finalValues[9] = value;
                    }

                    if ((name.equals("load_advanced") || name.equals("advanced") && setFalse)) {
                        finalValues[10] = "false";
                    }
                }

                for (int i = 0; i < size; i++) {
                    if (finalValues[i] == null) finalValues[i] = Settings.defaultValues[i];
                }
            } catch (Exception e) {
                Logger.getInstance().log("ERROR: argument parsing failed, check what you entered in the command line");
                Logger.getInstance().log(getStackTrace(e));
                System.exit(1);
            }
        }

        /* Use settings for certain initialization behavior */
        Settings settings = Settings.getSettings(finalValues);

        // open logger & log arguments if applicable
        if (settings.isDebugLog()) {
            Logger logger = Logger.getInstance();
            for (String s : args) {
                logger.log(s);
            }
        }

        // open debug console if applicable
        // open debug file menu if applicable
        if (settings.isOpenDebugConsoleOnExecution()) {
            main = new Runnable() {
                public void run() {
                    new DebugConsole(true);
                }
            };
        }
        
        if (settings.isRunDebugFile()) {
            try {
                DebugFileParse debugFile = new DebugFileParse(settings.getCustomDebugFile());
                main = debugFile.outputRunnable();
            } catch (IOException e) {
                Logger.getInstance().log("ERROR: debug file parsing failed, check what you entered in the command line");
                Logger.getInstance().log(getStackTrace(e));
                System.exit(1);
            }
        }

        /* Start GUI */
        try {
            SwingUtilities.invokeLater(main);
        } catch (Exception e) {
            Logger.getInstance().log("ERROR: main execution failed");
            Logger.getInstance().log(getStackTrace(e));
            System.exit(1);
        }
    }

}

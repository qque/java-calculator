package calculator;

import javax.swing.SwingUtilities;

import java.util.Properties;
import java.util.Enumeration;

import java.time.LocalDateTime; // get datetime for logs

import calculator.ui.CalculatorFrame;
import calculator.ui.DebugConsole;
import calculator.ui.NotepadFrame;
import jep.SharedInterpreter;
import calculator.Settings;
import calculator.logic.ButtonLogic;

public class Main {

    static Runnable main = new Runnable() {
        public void run() {
            new CalculatorFrame();
        } 
    };
    

    public static void main(String[] args) {
        Settings settings = Settings.getSettings();

        // capture arguments
        Properties properties = System.getProperties();
        Enumeration<?> propertyNames = properties.propertyNames();

        while (propertyNames.hasMoreElements()) {
            String name = (String) propertyNames.nextElement();
            String value = properties.getProperty(name);

            if ((name == "ODCOE" || name == "OPEN_DEBUG_CONSOLE_ON_EXECUTION") && value == "true") {
                main = new Runnable() {
                    public void run() {
                        new DebugConsole();
                    }
                };
            }
        }

        SwingUtilities.invokeLater(main);
    }
}

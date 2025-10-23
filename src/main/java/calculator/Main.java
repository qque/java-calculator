package calculator;

import javax.swing.SwingUtilities;

import java.util.Properties;
import java.util.Enumeration;

import java.time.LocalDateTime; // get datetime for logs

import calculator.ui.CalculatorFrame;
import calculator.ui.DebugConsole;
import calculator.ui.NotepadFrame;
import calculator.Settings;

public class Main {

    static Runnable main = new Runnable() {
        public void run() {
            new CalculatorFrame();
        } 
    };
    

    public static void main(String[] args) {
        // capture arguments
        Properties properties = System.getProperties();
        Enumeration<?> propertyNames = properties.propertyNames();

        while (propertyNames.hasMoreElements()) {
            String name = (String) propertyNames.nextElement();
            String value = properties.getProperty(name);

            if (name == "OPEN_DEBUG_ON_EXECUTION" && value == "true") {
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

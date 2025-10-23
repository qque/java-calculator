package calculator;

import javax.swing.SwingUtilities;

// get datetime for logs
import java.time.LocalDateTime;

import calculator.ui.CalculatorFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculatorFrame();
        });
    }
}

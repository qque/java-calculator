package calculator;

import javax.swing.SwingUtilities;

import calculator.ui.CalculatorFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculatorFrame();
        });
    }
}

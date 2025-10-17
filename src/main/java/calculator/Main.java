package calculator;

import javax.swing.SwingUtilities;

import calculator.ui.CalculatorFrame;

public class Main {
    // if set to 1, will output information about button/keyboard inputs and computation to the console
    public static final int DEBUG_MODE = 1;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculatorFrame();
        });
    }
}

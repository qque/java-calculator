/*
 *  Defines the behavior of buttons, including computation for "="
 *  Accepts any generic JTextArea using the setTextArea method
 */

package calculator.logic;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import calculator.ui.ButtonPanel.ButtonListener;
import calculator.History;

public class ButtonLogic implements ButtonListener {

    private static JTextArea display;

    public static void setTextArea(JTextArea area) { display = area; }

    // defines parsing and computation for "=" button
    public static double compute(String text) {
        

        return Double.NaN; // fail case, returns error on display side
    }

    // static implementation of onButtonClick behavior
    public static void runButton(String label) { 
        History history = History.getInstance();

        try {
            String expression = display.getText();
            String output = null;
            switch (label) {
                case "clear":
                    history.add(expression + " = " + compute(expression)); // send display to history
                    display.setText(null);
                    break;
                case "delete":
                    int caretPosition = display.getCaretPosition();
                    if (caretPosition > 0) {
                        display.getDocument().remove(caretPosition - 1, 1);
                    }
                    break;
                case "ans":
                    // `ans` will display "ans" to the user, and is dealt with in parsing
                    display.setText(display.getText() + label);
                    break;
                case "=":
                    double result = compute(expression);
                    if (Double.isNaN(result)) display.setText("ERROR");
                    else display.setText(result + "");
                    history.add(expression + " = " + result);
                    break;

                // input nothing
                case "(hyp.)":
                case "(rec.)":
                case "(deg.)":
                case "(rad.)": break;
                
                // input with modified symbol
                case "×": output = "*"; break;
                case "÷": output = "/"; break;
                case "1/x": output = "1/"; break;
                case "|x|": output = "|"; break;
                case "x²": output = "^2"; break;
                case "10ˣ": output = "10^"; break;
                case "eˣ": output = "e^"; break;
                
                // input with parenthesis (inverse trig & sqrt have modified symbol as well)
                case "sin": output = "sin("; break;
                case "cos": output = "cos("; break;
                case "tan": output = "tan("; break;
                case "sin⁻¹": output = "asin("; break;
                case "cos⁻¹": output = "acos("; break;
                case "tan⁻¹": output = "atan("; break;
                case "csc": output = "csc("; break;
                case "sec": output = "sec("; break;
                case "cot": output = "cot("; break;
                case "csc⁻¹": output = "acsc("; break;
                case "sec⁻¹": output = "asec("; break;
                case "cot⁻¹": output = "acot("; break;
                case "sinh": output = "sinh("; break;
                case "cosh": output = "cosh("; break;
                case "tanh": output = "tanh("; break;
                case "sinh⁻¹": output = "asinh("; break;
                case "cosh⁻¹": output = "acosh("; break;
                case "tanh⁻¹": output = "atanh("; break;
                case "√": output = "sqrt("; break;
                case "log": output = "log("; break;
                case "ln": output = "ln("; break;
                case "nPr": output = "nPr("; break;
                case "nCr": output = "nCr("; break;
                case "mean": output = "mean("; break;
                case "stdev": output = "stdev("; break;
                case "stdevp": output = "stdevp("; break;
                case "sort": output = "sort("; break;

                // input normally
                default:
                    display.setText(display.getText() + label);
                    break;
            }

            if (output != null) {
                display.setText(display.getText() + output);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                display,
                "An unexpected error occurred. Please try again.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    public void onButtonClick(String label) {
        runButton(label);
    }
}

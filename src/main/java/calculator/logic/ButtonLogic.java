/*
 *  Defines the behavior of buttons, including computation for "="
 *  ButtonLogic accepts any generic JTextArea using the setTextArea method
 */

package calculator.logic;

import javax.swing.JTextArea;

import calculator.ui.ButtonPanel.ButtonListener;

public class ButtonLogic implements ButtonListener {

    private static JTextArea display;

    public static void setTextArea(JTextArea area) { display = area; }

    // defines computation for "=" button
    public static double compute(String text) {
        // placeholder

        return Double.NaN; // fail case, returns error on display side
    }

    @Override
    public void onButtonClick(String label) {
        switch (label) {
            case "clear":
                // send display to history?
                display.setText(null);
                break;
            case "ans":
                
            case "=":
                double res = compute(display.getText());
                if (Double.isNaN(res)) display.setText("ERROR");
                else display.setText(res + "");
                break;
            case "(hyp.)":
            case "(rec.)":
            case "(deg.)":
            case "(rad.)":
                display.setText("");
                break;
            default:
                display.setText(display.getText() + label);
                break;
        }
    }
}

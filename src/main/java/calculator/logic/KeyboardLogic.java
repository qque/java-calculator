/*
 *  Defines the behavior of keyboard input
 *  All operations except ones that are directly input run through ButtonLogic.runButton
 *  Accepts any generic JTextArea using the setTextArea method
 */

package calculator.logic;

import javax.swing.JTextArea;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardLogic implements KeyListener {

    private static JTextArea display;

    public static void setTextArea(JTextArea area) { display = area; }

    @Override
    public void keyTyped(KeyEvent e) {} // not used

    @Override
    public void keyReleased(KeyEvent e) {} // not used

    // defines all keyboard input, 
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        String button = null;
        switch (key) {                          // '[button name on (my) keyboard]' -- `[button on calculator]
            // control operation
            case KeyEvent.VK_DELETE:            // 'Del' -- `clear`
                button = "clear"; break;
            case KeyEvent.VK_BACK_SPACE:        // 'Backspace' -- `delete`
                button = "delete"; break;
            case KeyEvent.VK_ENTER:             // 'Enter' -- `=`
                button = "="; break;

            // direct input
            case KeyEvent.VK_2:                 // '2' -- `2`
            case KeyEvent.VK_3:                 // '3' -- `3`
            case KeyEvent.VK_4:                 // '4' -- `4`
            case KeyEvent.VK_5:                 // '5' -- `5`
            case KeyEvent.VK_7:                 // '7' -- `7`
            case KeyEvent.VK_PERIOD:            // '.' -- `.`
            case KeyEvent.VK_PLUS:              // '+' -- `+`
            case KeyEvent.VK_MINUS:             // '-' -- `-`
            case KeyEvent.VK_SLASH:             // '/' -- `÷`
            case KeyEvent.VK_COMMA:             // ',' -- `,`
            case KeyEvent.VK_E:                 // 'e' -- `e`
                display.setText(display.getText() + (char)(key + 32)); // use key + 32 to get 'e' instead of 'E'
                break;

            // direct input (checking for special characters)
            case KeyEvent.VK_0:                 // '0' -- `0`
                if (e.isShiftDown()) button = ")";
                else button = "0";
                break;
            case KeyEvent.VK_1:                 // '1' -- `1`
                if (e.isShiftDown()) button = "!";
                else button = "1";
                break;
            case KeyEvent.VK_6:                 // '6' -- `6`
                if (e.isShiftDown()) button = "^";
                else button = "6";
                break;
            case KeyEvent.VK_8:                 // '8' -- `8`
                if (e.isShiftDown()) button = "*";
                else button = "8";
                break;
            case KeyEvent.VK_9:                 // '9' -- `9`
                if (e.isShiftDown()) button = "(";
                else button = "9";
                break;
            case KeyEvent.VK_BACK_SLASH:        // '|' -- `|x|`
                if (e.isShiftDown()) button = "|x|";
                break;

            // keys that will automatically type out a function/expression
            case KeyEvent.VK_S:                 // 's' -- `sin`
                button = "sin"; break;
            case KeyEvent.VK_C:                 // 'c' -- `cos`
                button = "cos"; break;
            case KeyEvent.VK_T:                 // 't' -- `tan`
                button = "tan"; break;
            case KeyEvent.VK_A:                 // 'a' -- `sin⁻¹`
                button = "sin⁻¹"; break;
            case KeyEvent.VK_X:                 // 'x' -- `cos⁻¹`
                button = "cos⁻¹"; break;
            case KeyEvent.VK_R:                 // 'r' -- `tan⁻¹`
                button = "tan⁻¹"; break;
            case KeyEvent.VK_M:                 // 'm' -- `mean`
                button = "mean"; break;
            case KeyEvent.VK_D:                 // 'd' -- `stdev`
                button = "stdev"; break;
            case KeyEvent.VK_V:                 // 'v' -- `stdevp`
                button = "stdevp"; break;
            case KeyEvent.VK_O:                 // 'o' -- `sort`
                button = "sort"; break;
            case KeyEvent.VK_P:                 // 'p' -- `π`
                button = "π"; break;
            case KeyEvent.VK_Q:                 // 'q' -- `√`
                button = "√"; break;
            case KeyEvent.VK_L:                 // 'l' -- `log`
                button = "log"; break;
            case KeyEvent.VK_N:                 // 'n' -- `ln`
                button = "ln"; break;

            // everything else (key press does nothing)
            default: break;
        }

        if (button != null) {
            ButtonLogic.runButton(button);
        }
    } 
}

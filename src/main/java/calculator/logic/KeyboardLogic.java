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
        boolean shift = e.isShiftDown();
        boolean ctrl = e.isControlDown();
        switch (key) {                          // '[button name on (my) keyboard]' -- `[button on calculator]
            // control operation
            case KeyEvent.VK_DELETE:            // 'Del' -- `clear`
                button = "clear"; break;
            case KeyEvent.VK_BACK_SPACE:        // 'Backspace' -- `delete`
                button = "delete"; break;
            case KeyEvent.VK_ENTER:             // 'Enter' -- `=`
                button = "="; break;
            case KeyEvent.VK_EQUALS:
                if (shift) button = "+";        // '+' -- `+`
                else button = "=";              // '=' -- `=`
            case KeyEvent.VK_SPACE:             // N/A; Allows user to input whitespace if they want
                display.setText(display.getText() + " ");
                break;

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
                display.setText(display.getText() + (char)(key));
                break;

            // direct input (checking for special characters)
            case KeyEvent.VK_0:                 // '0' -- `0`
                if (shift) button = ")";
                else button = "0";
                break;
            case KeyEvent.VK_1:                 // '1' -- `1`
                if (shift) button = "!";
                else button = "1";
                break;
            case KeyEvent.VK_6:                 // '6' -- `6`
                if (shift) button = "^";
                else button = "6";
                break;
            case KeyEvent.VK_8:                 // '8' -- `8`
                if (shift) button = "*";
                else button = "8";
                break;
            case KeyEvent.VK_9:                 // '9' -- `9`
                if (shift) button = "(";
                else button = "9";
                break;
            case KeyEvent.VK_BACK_SLASH:        // '|' -- `|x|`
                if (shift) button = "|x|";
                break;

            case KeyEvent.VK_A: 
            case KeyEvent.VK_B: 
            case KeyEvent.VK_C: 
            case KeyEvent.VK_D: 
            case KeyEvent.VK_E:                 // 'e' -- `e`
            case KeyEvent.VK_F: 
            case KeyEvent.VK_G: 
            case KeyEvent.VK_H: 
            case KeyEvent.VK_I: 
            case KeyEvent.VK_J: 
            case KeyEvent.VK_K: 
            case KeyEvent.VK_L: 
            case KeyEvent.VK_M: 
            case KeyEvent.VK_N: 
            case KeyEvent.VK_O: 
            case KeyEvent.VK_P: 
            case KeyEvent.VK_Q: 
            case KeyEvent.VK_R: 
            case KeyEvent.VK_S: 
            case KeyEvent.VK_T: 
            case KeyEvent.VK_U: 
            case KeyEvent.VK_V: 
            case KeyEvent.VK_W: 
            case KeyEvent.VK_X: 
            case KeyEvent.VK_Y: 
            case KeyEvent.VK_Z: 
                if (ctrl && key == KeyEvent.VK_H); // open history menu
                else if (ctrl && key == KeyEvent.VK_N); // open notepad
                else if (shift) display.setText(display.getText() + (char)(key));
                else display.setText(display.getText() + (char)(key + 32)); // use key + 32 to get lowercase
                break;

            // everything else (key press does nothing)
            default: break;
        }

        if (button != null) {
            ButtonLogic.runButton(button);
        }
    } 
}

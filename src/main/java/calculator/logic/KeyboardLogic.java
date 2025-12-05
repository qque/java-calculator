/*
 *  Defines the behavior of keyboard input
 *  All operations are run through ButtonLogic.runButton, including keys which are directly input
 *  For keys that are directly input, the label passed to runButton will be "null", and not do anything
 */

package calculator.logic;

import javax.swing.JTextArea;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import calculator.ui.DebugConsole;
import calculator.ui.HistoryFrame;
import calculator.ui.NotepadFrame;

public class KeyboardLogic implements KeyListener {

    private static JTextArea display;

    public static void setTextArea(JTextArea area) { display = area; }

    @Override
    public void keyTyped(KeyEvent e) {} // not used

    @Override
    public void keyReleased(KeyEvent e) {} // not used

    // defines all keyboard input
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        boolean shift = e.isShiftDown();
        boolean ctrl = e.isControlDown();
        String button = "null";

        switch (key) {                      // '[key name on (my) keyboard]' -- `[button on calculator]
        // control operation
        case KeyEvent.VK_DELETE:            // 'Del' -- `clear`
            button = "clear"; break;
        case KeyEvent.VK_BACK_SPACE:        // 'Backspace' -- `delete`
            button = "delete"; break;
        case KeyEvent.VK_ENTER:             // 'Enter' -- `=`
            if (shift) display.setText(display.getText() + '\n');
            else button = "=";
            break;
        case KeyEvent.VK_TAB:
            display.setText(display.getText() + '\t');
            break;
        case KeyEvent.VK_EQUALS:
            if (shift) button = "+";        // '+' -- `+`
            else if (ctrl) button = "/=";   //
            else button = "=";              // '=' -- `=`
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
        case KeyEvent.VK_2:                 // '2' -- `2`
            if (shift) button = "@";
            else button = "2";
            break;
        case KeyEvent.VK_3:                 // '3' -- `3`
            if (shift) button = "#";
            else button = "3";
            break;
        case KeyEvent.VK_4:                 // '4' -- `4`
            if (shift) button = "$";
            else button = "4";
            break;
        case KeyEvent.VK_5:                 // '5' -- `5`
            if (shift) button = "%";
            else button = "5";
            break;
        case KeyEvent.VK_6:                 // '6' -- `6`
            if (shift) button = "^";
            else button = "6";
            break;
        case KeyEvent.VK_7:                 // '7' -- `7`
            if (shift) button = "&";
            else button = "7";
            break;
        case KeyEvent.VK_8:                 // '8' -- `8`
            if (shift) button = "×";
            else button = "8";
            break;
        case KeyEvent.VK_9:                 // '9' -- `9`
            if (shift) button = "(";
            else button = "9";
            break;
        case KeyEvent.VK_MINUS:             // '-' -- `-`
            if (shift) button = "_";
            else button = "-";
            break;
        case KeyEvent.VK_SLASH:             // '/' -- `÷`
            if (shift) button = "?";
            else button = "÷";
            break;
        case KeyEvent.VK_PERIOD:            // '.' -- `.`
            if (shift) button = ">";
            else button = ".";
            break;
        case KeyEvent.VK_COMMA:             // ',' -- `,`
            if (shift) button = "<";
            else button = ",";
            break;
        case KeyEvent.VK_SEMICOLON:
            if (shift) button = ":";
            else button = ";";
            break;
        case KeyEvent.VK_BACK_SLASH:        // '|' -- `|x|`
            if (shift) button = "|x|";
            break;

        case KeyEvent.VK_SPACE:
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
            if (ctrl && key == KeyEvent.VK_H) {
                button = "^H";
                HistoryFrame historyFrame = new HistoryFrame();
                historyFrame.setVisible(true);
            } else if (ctrl && key == KeyEvent.VK_N) {
                button = "^N";
                NotepadFrame notepadFrame = new NotepadFrame();
                notepadFrame.setVisible(true);
            } else if (ctrl && key == KeyEvent.VK_D) {
                button = "^D";
                DebugConsole debugConsole = new DebugConsole();
                debugConsole.setVisible(true);
            } else {
                button = e.getKeyChar() + "";
            }
            break;

        case KeyEvent.VK_SHIFT:
        case KeyEvent.VK_CONTROL:
            button = "DO NOTHING";
            break;

        // everything else (key press does nothing)
        default: break;
        }

        // runButton is always ran here, even when it is "null", since we want the baseline
        // behavior of runButton (setting display text to black, setting cursor, etc.) to occur
        ButtonLogic.runButton(button);
    } 
}

/*
 *  NOTE: This feature is in progress, and is not part of the final project submission
 *  
 *  The debug console is a debugging tool, opened by ctrl + D, that allows you to input code
 *  directly (straight python code) or indirectly (text that gets preprocessed and computed by ButtonLogic)
 *  to test out functions. It is also possible to pass file input for programmatic testing.
 */

package calculator.ui;

import javax.script.ScriptException;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.event.*;

import calculator.logic.ButtonLogic;
import calculator.logic.Engine;

import calculator.History;
import calculator.Settings;

public class DebugConsole extends JFrame implements KeyListener {

    private static History history = History.getHistory();

    private static Settings settings = Settings.getSettings();

    private JTextArea textArea;

    @SuppressWarnings("unused")
    private volatile String text;
    
    public DebugConsole() {
        this(false);
    }

    public DebugConsole(boolean large) {
        super("Debug Console");
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // large size is 800x800, regular is 400x400
        setSize(large ? 800 : 400, large ? 800 : 400);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        initUI();

        Engine.eval("pass");
    }

    private void initUI() {
        textArea = new JTextArea();
        textArea.setBounds(getBounds());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        
        getContentPane().setLayout(new BorderLayout());

        // update when content changes
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            private void updateText() {
                text = textArea.getText();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateText();
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.isShiftDown()) {
                // pass input directly

                Engine.eval(text);
            } else if (e.isControlDown()) {
                // pass input indirectly

                try {
                    String result = ButtonLogic.compute(text).toString();

                    if (settings.isDisplayDebugConsoleOut()) { 
                        JOptionPane.showMessageDialog(
                            textArea,
                            result,
                            "Debug Console Output",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }

                    System.out.println("DEBUG CONSOLE:\n\n" + text + ",   " + result);
                    
                    if (settings.isSendDebugConsoleOutToHist()) {
                        history.add(text, result);
                    }
                } catch (ScriptException err) {
                    System.out.println(err);
                }
            }
        } else {
            textArea.setText(textArea.getText() + "\n");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // not used
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // not used
    }
    
}

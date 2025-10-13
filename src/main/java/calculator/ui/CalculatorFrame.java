/*
 *  Defines and implements the core GUI components of the application.
 *  Complex operations and separate ui frames are offloaded to other classes.
 */

package calculator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import calculator.logic.*;

public class CalculatorFrame extends JFrame {

    private JTextArea display;

    // initialize calculator frame
    public CalculatorFrame() {
        super("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(685, 635);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // top display for calculator output
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));

        display = new JTextArea();
        display.setEditable(false);

        Listener keyListener = new Listener();
        display.addKeyListener(keyListener); // key listener

        display.setFont(new Font("Consolas", Font.PLAIN, 47));
        display.setLineWrap(true); // wrap text
        display.setWrapStyleWord(true);
        display.setPreferredSize(new Dimension(400, 80));
        topPanel.add(display, BorderLayout.CENTER);

        // clear and ans buttons beside top display
        JPanel sideButtons = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton clearButton = new JButton("clear");
        JButton ansButton = new JButton("ans");

        clearButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        ansButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        // class defining button behavior
        ButtonLogic clickBehavior = new ButtonLogic();
        ButtonLogic.setTextArea(display);
        // connect clear and ans buttons to listener
        clearButton.addActionListener(e -> clickBehavior.onButtonClick("clear"));
        ansButton.addActionListener(e -> clickBehavior.onButtonClick("delete"));

        clearButton.setPreferredSize(new Dimension(100, 80));
        ansButton.setPreferredSize(new Dimension(100, 80));

        sideButtons.add(clearButton);
        sideButtons.add(ansButton);
        topPanel.add(sideButtons, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 8x6 button grid below top display (see ButtonPanel.java)
        ButtonPanel buttonPanel = new ButtonPanel(clickBehavior);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    // KeyListener implementation
    public class Listener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            // not used
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // not used
        }

        @Override
        public void keyTyped(KeyEvent e) {
            char key = e.getKeyChar();
            switch (key) {
                // keys that preform a control operation
                case 0x7F: 
                // keys that can be directly typed
                case '.':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '+':
                case '-':
                case '/':
                case '*':
                case '^':
                case '!':
                case ',':
                case '(':
                case ')': display.setText(display.getText() + key);

                // keys that will automatically type out a function/expression
                case 's':

                // everything else (does nothing)
                default: break;
            }
        } 
    }

}
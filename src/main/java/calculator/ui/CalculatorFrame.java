/*
 *  Defines and implements the core GUI components of the application.
 *  The basic frame and button 
 */

package calculator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import calculator.logic.*;

public class CalculatorFrame extends JFrame implements ButtonPanel.ButtonClickListener, KeyListener {

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
        display.addKeyListener(this); // key listener
        display.setEditable(false);
        display.setFont(new Font("Consolas", Font.BOLD, 47));
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
        clearButton.addActionListener(e -> onButtonClick("clear")); // connect clear and ans buttons to listener
        ansButton.addActionListener(e -> onButtonClick("ans"));
        clearButton.setPreferredSize(new Dimension(100, 80));
        ansButton.setPreferredSize(new Dimension(100, 80));
        sideButtons.add(clearButton);
        sideButtons.add(ansButton);
        topPanel.add(sideButtons, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 8x6 button grid below top display (see ButtonPanel.java)
        ButtonPanel buttonPanel = new ButtonPanel(this);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    // defined behavior for button clicks, the more complex operations are abstracted into logic/
    @Override
    public void onButtonClick(String label) {
        switch (label) {
            case "clear":
                // send display to history?
                display.setText(null);
                break;
            case "=":
                double res = CalculatorEval.compute(display.getText());
                String out = res + "";
                if (Double.isNaN(res)) out = "ERROR";
                display.setText(out);
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
        if (true);
    }
    
}
/*
 *  Defines and implements the core GUI components of the application.
 *  Complex operations and separate ui frames are offloaded to other classes.
 */

package calculator.ui;

import javax.swing.*;
import java.awt.*;

import calculator.logic.*;
import calculator.Settings;

public class CalculatorFrame extends JFrame {

    private JTextArea display;

    // initialize calculator frame
    public CalculatorFrame() {
        super("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(685, 685);
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

        JScrollPane scrollPane = new JScrollPane(display);

        KeyboardLogic keyListener = new KeyboardLogic();
        KeyboardLogic.setTextArea(display);
        display.addKeyListener(keyListener); // key listener

        display.setFont(new Font("Consolas", Font.PLAIN, Settings.consoleFontSize));
        display.setLineWrap(true); // wrap text
        display.setWrapStyleWord(true);
        display.setPreferredSize(new Dimension(400, 80));
        topPanel.add(scrollPane, BorderLayout.CENTER);
        topPanel.add(display, BorderLayout.CENTER);


        // clear and ans buttons beside top display
        JPanel sideButtons = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton clearButton = new JButton("clear");
        JButton deleteButton = new JButton("delete");

        clearButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        deleteButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        ButtonLogic clickBehavior = new ButtonLogic(); // class defining button behavior
        ButtonLogic.setTextArea(display);
        
        // connect clear and ans buttons to listener manually
        clearButton.addActionListener(e -> clickBehavior.onButtonClick("clear"));
        deleteButton.addActionListener(e -> clickBehavior.onButtonClick("delete"));

        clearButton.setPreferredSize(new Dimension(100, 80));
        deleteButton.setPreferredSize(new Dimension(100, 80));

        sideButtons.add(clearButton);
        sideButtons.add(deleteButton);
        topPanel.add(sideButtons, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);


        // setup JS parsing engine (preloads js functions)
        ButtonLogic.setupEngine();

    
        // 9x6 button grid below top display (see ButtonPanel.java)
        ButtonPanel buttonPanel = new ButtonPanel(clickBehavior);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

}
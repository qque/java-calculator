/*
 *  Defines and implements the core GUI components of the application
 */

package calculator.ui;

import javax.swing.*;
import java.awt.*;

import calculator.logic.ButtonLogic;
import calculator.logic.Engine;
import calculator.logic.KeyboardLogic;

import calculator.Settings;

public class CalculatorFrame extends JFrame {

    private static Settings settings = Settings.getSettings();

    private Font consoleFont = new Font(settings.getConsoleFontName(), settings.getConsoleFontStyle(), settings.getConsoleFontSize());
    private Font buttonFont = new Font(settings.getButtonFontName(), settings.getButtonFontStyle(), settings.getButtonFontSize());

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

        JScrollPane scrollPane = new JScrollPane(display, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        KeyboardLogic keyListener = new KeyboardLogic();
        KeyboardLogic.setTextArea(display);
        display.addKeyListener(keyListener); // key listener

        display.setFont(consoleFont);
        display.setLineWrap(true); // wrap text
        display.setWrapStyleWord(true);
        display.setPreferredSize(new Dimension(400, 80));
        topPanel.add(scrollPane, BorderLayout.CENTER);

        // clear and ans buttons beside top display
        JPanel sideButtons = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton clearButton = new JButton("clear");
        JButton deleteButton = new JButton("delete");

        clearButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);
        
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
    
        // 9x6 button grid below top display (see ButtonPanel.java)
        ButtonPanel buttonPanel = new ButtonPanel(clickBehavior);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // force engine to load into memory and evaluate functions.py
        // this is done so there isn't a ~0.5 second pause when the user enters their first calculation
        Engine.eval("pass");

        add(mainPanel);
    }

}
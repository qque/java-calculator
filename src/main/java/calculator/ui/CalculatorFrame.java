package calculator.ui;

import javax.swing.*;
import java.awt.*;

public class CalculatorFrame extends JFrame {

    private JTextField display;

    // initialize calculator frame
    public CalculatorFrame() {
        super("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 900);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("SansSerif", Font.BOLD, 32));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.WHITE);
        display.setPreferredSize(new Dimension(400, 80));
        mainPanel.add(display, BorderLayout.NORTH);

        // button grid (8 x 6)
        JPanel buttonPanel = new JPanel(new GridLayout(8, 6, 8, 8));

        for (int i = 1; i <= 48; i++) {
            JButton button = new JButton("B" + i);
            button.setFont(new Font("SansSerif", Font.BOLD, 18));
            buttonPanel.add(button);
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
}
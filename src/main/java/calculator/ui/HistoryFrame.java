package calculator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import calculator.History;

public class HistoryFrame extends JFrame {

    public HistoryFrame() {
        super("History");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 300); // remove when pack() is used
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        initUI();
        // uncomment when initUI is implemented
        //pack();
    }

    private void initUI() {
        // todo
    }
    
}

package calculator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import calculator.History;

public class HistoryFrame extends JFrame {

    private static History history = History.getInstance();

    public HistoryFrame() {
        super("History");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        initUI();
    }

    private void initUI() {
        // todo
    }
    
}

package calculator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import calculator.History;
import calculator.Settings;

public class SettingsFrame extends JFrame {

    private static History history = History.getInstance();

    public SettingsFrame() {
        super("Settings");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        initUI();
    }

    private void initUI() {
        // todo
    }

    // update settings, ensure consistency 
    private void ensureConsistent() {
        // todo
    }
    
}

package calculator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsFrame extends JFrame {

    public SettingsFrame() {
        super("Settings");
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

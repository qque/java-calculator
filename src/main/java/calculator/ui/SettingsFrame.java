package calculator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsFrame extends JFrame {

    public SettingsFrame() {
        super("Settings");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setLayout(new FlowLayout());

        pack(); 
    }
    
}

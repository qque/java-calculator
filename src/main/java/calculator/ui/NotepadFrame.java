package calculator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import calculator.History;

public class NotepadFrame extends JFrame {

    public NotepadFrame() {
        super("Notepad");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setLayout(new FlowLayout());

        pack(); 
    }

}

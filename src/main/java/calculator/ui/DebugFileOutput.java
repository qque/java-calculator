package calculator.ui;

import java.awt.*;
import javax.swing.*;

import calculator.Logger;
import calculator.Settings;

public class DebugFileOutput extends JFrame {

    private static Settings settings = Settings.getSettings();
    private static final boolean DEBUG_MODE = settings.isDebugMode();

    private static Logger logger = Logger.getInstance();
    private static final boolean DEBUG_LOG = settings.isDebugLog();

    private JTextArea textArea;

    public DebugFileOutput(String text) {
        super("Debug File Output");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        initUI();

        textArea.setText(text);

        if (DEBUG_MODE) {
            System.out.println("DEBUG FILE OUTPUT OPENED");
        }
        if (DEBUG_LOG) {
            logger.log("DEBUG FILE OUTPUT OPENED");
        }
    }

    private void initUI() {
        textArea = new JTextArea();
        textArea.setBounds(getBounds());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 27));
        
        getContentPane().setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }
    
}

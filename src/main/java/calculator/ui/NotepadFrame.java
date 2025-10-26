package calculator.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import calculator.History;
import calculator.Logger;
import calculator.Settings;

public class NotepadFrame extends JFrame implements KeyListener {

    private static History history = History.getHistory();

    private static Settings settings = Settings.getSettings();
    private static boolean DEBUG_MODE = settings.getDebugMode();

    private static Logger logger = Logger.getInstance();
    private static boolean DEBUG_LOG = settings.getDebugLog();

    private JTextArea textArea;

    @SuppressWarnings("unused")
    private volatile String text;

    public NotepadFrame() {
        super("Notepad");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        initUI();

        if (DEBUG_MODE) {
            System.out.println("NOTEPAD OPENED");
        }
        if (DEBUG_LOG) {
            logger.log("NOTEPAD OPENED");
        }
    }

    private void initUI() {
        textArea = new JTextArea();
        textArea.setBounds(getBounds());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        
        getContentPane().setLayout(new BorderLayout());

        // update when content changes
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            private void updateText() {
                text = textArea.getText();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateText();
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {
            if (DEBUG_MODE) {
                System.out.println("Accessed output from history");
            }
            if (DEBUG_LOG) {
                
            }

            textArea.setText(textArea.getText() + history.getLatestToString());
        } else {
            textArea.setText(textArea.getText() + e.getKeyChar());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // not used
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // not used
    }

}

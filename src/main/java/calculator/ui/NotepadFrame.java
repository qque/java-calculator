package calculator.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import calculator.History;
import calculator.Logger;
import calculator.Main;
import calculator.Settings;

public class NotepadFrame extends JFrame {

    private static History history = History.getHistory();

    private static Settings settings = Settings.getSettings();
    private static boolean DEBUG_MODE = settings.isDebugMode();

    private static Logger logger = Logger.getInstance();
    private static boolean DEBUG_LOG = settings.isDebugLog();

    private JTextArea textArea;

    public NotepadFrame() {
        super("Notepad");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        initUI();

        textArea.setText(Main.text);

        if (DEBUG_MODE) {
            System.out.println("NOTEPAD OPENED");
        }
        if (DEBUG_LOG) {
            logger.log("NOTEPAD OPENED");
        }
    }

    private void initUI() {
        textArea = new JTextArea();
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.text = textArea.getText();
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(scrollPane, BorderLayout.CENTER);

        InputMap im = textArea.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = textArea.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "insertHistory");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK), "insertHistory");

        am.put("insertHistory", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String latest = history.getLatestToString();

                int pos = textArea.getCaretPosition();
                textArea.insert(latest, pos);

                textArea.setCaretPosition(pos + latest.length());
            }
        });
    }

}

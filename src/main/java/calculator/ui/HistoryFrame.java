package calculator.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import calculator.History;

public class HistoryFrame extends JFrame {

    private static History history = History.getHistory();
    private DefaultListModel<String> listModel; // stores formatted history entries
    private JList<String> historyList;

    public HistoryFrame() {
        setTitle("Calculator History");
        setSize(350, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
        refresh();
    }

    public void initUI() {
        listModel = new DefaultListModel<>();
        historyList = new JList<>(listModel);
        historyList.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(historyList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JButton clearButton = new JButton("Clear History");
        clearButton.addActionListener(e -> {
            history.clear();
            refresh();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(clearButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // refreshes the displayed history.
    public void refresh() {
        listModel.clear();

        ArrayList<ArrayList<String>> stored = history.getHistoryArray(); 

        for (ArrayList<String> entry : stored) {
            if (entry.size() >= 2) {
                String input = entry.get(0);
                String output = entry.get(1);
                listModel.addElement(input + " → " + output);
            }
        }

        if (!listModel.isEmpty()) {
            historyList.ensureIndexIsVisible(listModel.size() - 1);
        }
    }
    
}

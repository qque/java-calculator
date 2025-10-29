/*
 *  Defines the behavior parsing for custom debug files.
 *  
 *  When the user gives a debug file to run, it will evaluate each line in the file (excluding comments)
 *  as it's own calculation, then display the result (with comments preserved) in a new frame.
 */

package calculator.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

import javax.swing.JTextArea;

import calculator.ui.DebugFileOutput;

import calculator.History;

public class DebugFileParse {

    private static History debugFileOutput = History.getHistory();
    
    public DebugFileParse(String path) throws IOException {
        Engine.eval("pass");

        ArrayList<String> lines = getFileLines(path);

        for (String l : lines) {
            char first = l.charAt(0);

            if (l.isBlank() || first == '#' || first == ';') { // comment/skipped line
                debugFileOutput.add("null","\n");
                continue;
            }

            l = l.strip();

            if (first == '!') { // load subfile within file
                try {
                    getFileLines(l.substring(1));
                    // todo
                } catch (IOException e) {
                    // todo
                }
            } else { // evaluate anything else as a calculation

                // create a dummy JTextArea of just the line, and make ButtonLogic use it
                // this makes it so runButton with label "=" will deal with this line how one would want it to
                // we can get the result simply by capturing the JTextArea's text after runButton("=")
                JTextArea input = new JTextArea(l);
                ButtonLogic.setTextArea(input);

                ButtonLogic.runButton("=");

                String result = input.getText();

                debugFileOutput.add(l, result);
            }
        }
    }

    public Runnable outputRunnable() {
        StringBuilder text = new StringBuilder();

        for (ArrayList<String> historyLine : debugFileOutput.getHistoryArray()) {
            if (historyLine.get(0) == "null") {
                text.append(historyLine.get(1));
            }

            text.append(History.historyLineToString(historyLine) + "\n");
        }

        return new Runnable() {
                public void run() {
                    new DebugFileOutput(text.toString());
                }
            };
    }

    private static ArrayList<String> getFileLines(String path) throws IOException {
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }

}

/*
 *  Defines the behavior parsing for custom debug files.
 *  
 *  When the user gives a debug file to run, it will evaluate each line in the file (excluding comments)
 *  as it's own calculation, then display the result (with comments preserved) in a new frame.
 */

package calculator.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.JTextArea;

import calculator.ui.DebugFileOutput;

import calculator.Logger;
import calculator.History;

public class DebugFileParse {

    private static History debugFileOutput = History.getHistory();
    
    public DebugFileParse(String path) throws IOException {
        Engine.eval("pass");

        String[] lines = getFileLines(path);

        try {
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
        } catch (Exception e) {
            Logger.getInstance().log("ERROR: debug file parsing failed");
            Logger.getInstance().log(Logger.getStackTrace(e));
            System.exit(1);
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

    // todo, returning null
    private static String[] getFileLines(String path) throws IOException {
        return Files.readAllLines(Path.of(path)).toArray(new String[0]);
    }

}

/* 
 *  Defines the 9x6 main button area
 *  This includes everything except the "ans" and "clear buttons" (and the side panel)
 *  Those buttons are not defined here since they are contained in the top panel
 *  
 *  NOTE: The grid can easily modified by changing `labels`, but any newly defined
 *        behavior will have to be in ButtonLogic (and possibly in other classes),
 *        and the window size will need to change if you change the 8x6 size.
 *  
 *        However, if you simply want to move around buttons, no other modifications
 *        need to be made, since behavior is managed in ButtonLogic by the label
 *        of the button, not by it's position. 
*/

package calculator.ui;

import javax.swing.*;
import java.awt.*;

import java.util.HashMap;
import java.util.Map;

public class ButtonPanel extends JPanel {

    public interface ButtonListener {
        void onButtonClick(String label);
    }

    private final Map<String, JButton> buttons = new HashMap<>();

    @SuppressWarnings("unused")
    private final ButtonListener listener;
    
    // main 9x6 grid labels
    public static String[][] labels = {
        {"list" , "matrix", "test"  , "solve"  , "graph" , "->"},
        {"stat" , "calc"  , "signal", "special", "cmplx" , "MENUS"},
        {"sin⁻¹", "cos⁻¹" , "tan⁻¹" , "(hyp.)" , "i"     , "π"},
        {"sin"  , "cos"   , "tan"   , "(rec.)" , "(deg.)", "e"},
        {"("    , ")"     , ","     , "1/x"    , "|x|"   , "ln"},
        {"7"    , "8"     , "9"     , "÷"      , "^"     , "eˣ"},
        {"4"    , "5"     , "6"     , "×"      , "√"     , "log"},
        {"1"    , "2"     , "3"     , "-"      , "x²"    , "10ˣ"},
        {"0"    , "."     , "ans"   , "+"      , "="     , "="}
    };

    // sub-labels for the popup menus
    // will generally be n-by-6, n ranging from 2 to 7
    
    // list editing & operations, similar to 
    public static String[][] listPopupLabels = {
        {"mean", "stdev", "stdevp"},
        {"erf", "example", "example2"}
    };

    public static String[][] matrixPopupLabels = {
        {"mean", "stdev", "stdevp"},
        {"erf", "example", "example2"}
    };

    public static String[][] testPopupLabels = {
        {"==", ">", "<", ">=", "<=", "∈"},
        {"AND", "OR", "XOR", "NOT", "IF", ""},
        {"ALL", "ANY", "PIECEWISE", "sgn", "dirac", ""}
    };

    public static String[][] solvePopupLabels = {
        {"mean", "stdev", "stdevp"},
        {"erf", "example", "example2"}
    };

    // graphing
    public static String[][] graphPopupLabels = {
        {"mean", "stdev", "stdevp"},
        {"erf", "example", "example2"}
    };

    // functions for probability and statistics
    public static String[][] statPopupLabels = {
        // descriptive stats
        {"mean", "stdev", "stdevp", "var", "varp", "cov"},
        {"sort", "median", "q1", "q3", "iqr", "quartile"},

        // probability
        {"nCr", "nPr", "!", "", "", ""},
    };

    // calculus & differential equations
    public static String[][] calcPopupLabels = {
        {"mean", "stdev", "stdevp"},
        {"erf", "example", "example2"}
    };

    // fast fourier transform and related operations
    // some functions are not necessarily related to signals processing,
    // more broadly this is for any kind of engineering/physics 
    public static String[][] signalPopupLabels = {
        {"mean", "stdev", "stdevp"},
        {"erf", "example", "example2"}
    };

    // special functions (gamma, bessel, etc.)
    public static String[][] specialPopupLabels = {
        {"mean", "stdev", "stdevp"},
        {"erf", "example", "example2"}
    };

    // complex number functions & utilities
    public static String[][] cmplxPopupLabels = {
        {"imag", "real", "conj"},
    };

    // separate ui windows
    // typically opened with ctrl+h, ctrl+s, etc., but also accessable here
    public static String[][] menusPopupLabels = {
        {"history", "notepad", "settings"},
        {"erf", "example", "example2"}
    };


    // fully initializes popup menu for a given button
    // used to avoid code repetition in `stat`, `test`, etc.
    private void createPopup(ButtonListener listener, JButton button, String[][] popupLabels) {
        int rows = popupLabels.length;
        int cols = popupLabels[0].length;

        JPopupMenu popupMenu =  new JPopupMenu();

        JPanel gridPanel = new JPanel(new GridLayout(rows, cols, 5, 5));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JButton optionButton = new JButton(popupLabels[row][col]);
                optionButton.setFont(new Font("SansSerif", Font.BOLD, 18));

                // add listener for buttons in popup menu
                optionButton.addActionListener(e -> {
                    if (listener != null) listener.onButtonClick(optionButton.getText());
                    popupMenu.setVisible(false);
                });

                gridPanel.add(optionButton);
            }
        }

        popupMenu.add(gridPanel);

        button.addActionListener(e -> {
            popupMenu.show(button, 0, button.getHeight());
        });
    };


    public ButtonPanel(ButtonListener listener) {
        this.listener = listener;
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        for (int row = 0; row < labels.length; row++) {
            for (int col = 0; col < labels[row].length; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                String label = labels[row][col];

                JButton button = new JButton(label);
                button.setFont(new Font("SansSerif", Font.BOLD, 18));

                Dimension fixed = new Dimension(100, 40);
                button.setPreferredSize(fixed);
                button.setMinimumSize(fixed);
                button.setMaximumSize(fixed);

                buttons.put(label, button);

                // merge last two "=" buttons into one wide cell
                if (row == 8 && col == 4) {
                    gbc.gridwidth = 2;
                    add(button, gbc);
                    col++;
                    gbc.gridwidth = 1;
                } else {
                    add(button, gbc);
                }

                // popup menu for certain buttons
                // createPopup is used both to create the popup and to pass it along to onButtonClick
                if (row == 0 && col == 0) createPopup(listener, button, listPopupLabels);
                else if (row == 0 && col == 1) createPopup(listener, button, matrixPopupLabels);
                else if (row == 0 && col == 2) createPopup(listener, button, testPopupLabels);
                else if (row == 0 && col == 3) createPopup(listener, button, solvePopupLabels);
                else if (row == 0 && col == 4) createPopup(listener, button, graphPopupLabels);
                else if (row == 1 && col == 0) createPopup(listener, button, statPopupLabels);
                else if (row == 1 && col == 1) createPopup(listener, button, calcPopupLabels);
                else if (row == 1 && col == 2) createPopup(listener, button, signalPopupLabels);
                else if (row == 1 && col == 3) createPopup(listener, button, specialPopupLabels);
                else if (row == 1 && col == 4) createPopup(listener, button, cmplxPopupLabels);
                else if (row == 1 && col == 5) createPopup(listener, button, menusPopupLabels);

                // listener for button clicks
                button.addActionListener(e -> {
                    if (listener != null) listener.onButtonClick(button.getText());
                });
            }
        }

        // label changes for hyp, rec, and deg
        buttons.get("(hyp.)").addActionListener(e -> toggleHyperbolic());
        buttons.get("(rec.)").addActionListener(e -> toggleReciprocal());
        buttons.get("(deg.)").addActionListener(e -> toggleDegree());
    }


    // definitions for hyp, rec, and deg label changes
    public static boolean isHyperbolic = false;
    public static boolean isReciprocal = false;
    public static boolean isDegree = false;

    private void toggleHyperbolic() {
        isHyperbolic = !isHyperbolic;

        buttons.get("sin").setText(isHyperbolic ? "sinh" : "sin");
        buttons.get("cos").setText(isHyperbolic ? "cosh" : "cos");
        buttons.get("tan").setText(isHyperbolic ? "tanh" : "tan");
        buttons.get("sin⁻¹").setText(isHyperbolic ? "sinh⁻¹" : "sin⁻¹");
        buttons.get("cos⁻¹").setText(isHyperbolic ? "cosh⁻¹" : "cos⁻¹");
        buttons.get("tan⁻¹").setText(isHyperbolic ? "tanh⁻¹" : "tan⁻¹");
    }

    private void toggleReciprocal() {
        isReciprocal = !isReciprocal;

        buttons.get("sin").setText(isReciprocal ? "csc" : "sin");
        buttons.get("cos").setText(isReciprocal ? "sec" : "cos");
        buttons.get("tan").setText(isReciprocal ? "cot" : "tan");
        buttons.get("sin⁻¹").setText(isReciprocal ? "csc⁻¹" : "sin⁻¹");
        buttons.get("cos⁻¹").setText(isReciprocal ? "sec⁻¹" : "cos⁻¹");
        buttons.get("tan⁻¹").setText(isReciprocal ? "cot⁻¹" : "tan⁻¹");
    }

    private void toggleDegree() {
        isDegree = !isDegree;

        buttons.get("(deg.)").setText(isDegree ? "(rad.)" : "(deg.)");
    }

}
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

import calculator.Settings;

public class ButtonPanel extends JPanel {

    private static Settings settings = Settings.getSettings();

    public interface ButtonListener {
        void onButtonClick(String label);
    }

    private Font buttonFont = new Font(settings.getButtonFontName(), settings.getButtonFontStyle(), settings.getButtonFontSize());
    private Font popupButtonFont = new Font(settings.getPopupButtonFontName(), settings.getPopupButtonFontStyle(), settings.getPopupButtonFontSize());

    private final Map<String, JButton> buttons = new HashMap<>();

    @SuppressWarnings("unused")
    private final ButtonListener listener;
    
    
    /* All button label definitions */
    public static final String[][] labels = {
        {"list" , "matrix", "test"   , "solve" , "graph" , "->"   },
        {"stat" , "num"   , "special", "calc"  , "cmplx" , "const"},
        {"sin⁻¹", "cos⁻¹" , "tan⁻¹"  , "(hyp.)", "i"     , "π"    },
        {"sin"  , "cos"   , "tan"    , "(rec.)", "(deg.)", "e"    },
        {"("    , ")"     , ","      , "1/x"   , "|x|"   , "ln"   },
        {"7"    , "8"     , "9"      , "÷"     , "^"     , "eˣ"   },
        {"4"    , "5"     , "6"      , "×"     , "√"     , "log"  },
        {"1"    , "2"     , "3"      , "-"     , "x²"    , "10ˣ"  },
        {"0"    , "."     , "ans"    , "+"     , "="     , "="    }
    };
    
    // list variables & operations
    public static final String[][] listPopupLabels = {
        {"mean", "stdev", "stdevp"},
        {"erf", "example", "example2"}
    };

    // matrix variables & operations (like ti84, some basic operations work with matricies)
    public static final String[][] matrixPopupLabels = {
        {"mean", "stdev", "stdevp"},
        {"erf", "example", "example2"}
    };

    // test/logic operators
    public static final String[][] testPopupLabels = {
        {"==", ">", "<", ">=", "<=", "∈"},
        {"AND", "OR", "XOR", "NOT", "IF", ""},
        {"ALL", "ANY", "PIECEWISE", "sgn", "dirac", ""}
    };

    public static final String[][] solvePopupLabels = {
        {"mean", "stdev", "stdevp"},
        {"erf", "example", "example2"}
    };

    // graphing
    public static final String[][] graphPopupLabels = {
        {"mean", "stdev", "stdevp"},
        {"erf", "example", "example2"}
    };

    // functions for probability and statistics (these are the labels for the sub-popup menus)
    public static final String[][] statPopupLabels = {
        {"descriptive"     , "correlation"   , "regression"       , "frequency"    , "applied"      },
        {"continuous dist.", "discrete dist.", "analytic prob"    , "combinatorics", "stochastic"   },
        {"hypothesis"      , "confidence"    , "dynamical systems", "time series"  , "survival"     },
        {"effect sizes"    , "bayesian"      , "multivariate"     , "nonparametric", "miscellaneous"}
    };

    // this is divided into multiple sub categories due to how many functions there are
    public static final String[][][] statSubLabels = {
        // descriptive stats
        {
            {"mean"  , "stdev", "stdevp", "variance"  , "skewness", "kurtosis" },
            {"median", "mode" , "sort"  , "percentile", "iqr"     , "mad"      },
            {"min"   , "max"  , "range" , "midrange"  , "mode"    , "trim"     },
            {"wmean" , "gmean", "hmean" , "qmean"     , "pmean"   , "quasimean"},
            {"weigh" , "wgm"  , "whm"   , "wqm"       , "wpm"     , "wqam"     }
        },

        // correlation
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // regression
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // frequency
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // applied
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // continuous distributions
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // discrete distributions
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // analytic probability
        {
            {"nCr", "nPr", "!", "", "", ""},
        },


        // combinatorics
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // stochastic
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // hypothesis
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // confidence
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // dynamical systems
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // time series
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // survival
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // bayesian
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // multivariate
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // nonparametric
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // effect sizes
        {
            {"nCr", "nPr", "!", "", "", ""},
        },

        // miscellaneous
        {
            {"nCr", "nPr", "!", "", "", ""},
        },
    };


    // number theory
    public static final String[][] numberPopupLabels = {
        {"something...", "primes?", "sequences?"},
    };

    public static final String[][][] numberSubLabels = {
        {
            {"...", "...", "..."}
        },

        {
            {"...", "...", "..."}
        },

        {
            {"...", "...", "..."}
        }
    };


    // calculus & differential equations
    public static final String[][] calcPopupLabels = {
        {"mean", "stdev", "stdevp"},
        {"erf", "example", "example2"}
    };


    // special functions (gamma, bessel, etc.)
    // todo: expand
    public static final String[][] specialPopupLabels = {
        {"mean", "stdev", "stdevp"},
        {"erf", "example", "example2"}
    };


    // complex number functions & utilities
    public static final String[][] cmplxPopupLabels = {
        {"imag", "real", "conj"},
    };


    // wide variety of constants, divided into a couple of categories for ease of access
    // e, pi, and i are on the base button panel since they are used so frequently
    public static final String[][] constantsPopupLabels = {
        {"trivial", "transcendental", "scientific"}
    };

    public static final String[][][] constantsSubLabels = {
        // trivial constants, i.e. those that can be achieved through a finite number of algebraic operations
        {
            {"...", "...", "..."}
        },

        // transcendental constants
        {
            {"...", "...", "..."}
        },

        // constants used outside of math in physics, engineering, etc.
        {
            {"...", "...", "..."}
        }
    };


    // fully initializes popup menu for a given button
    // used to avoid code repetition in `stat`, `test`, etc.
    private void createPopup(JButton button, String[][] popupLabels) {
        int rows = popupLabels.length;
        int cols = popupLabels[0].length;

        JPopupMenu popupMenu =  new JPopupMenu();

        JPanel gridPanel = new JPanel(new GridLayout(rows, cols, 5, 5));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JButton optionButton = new JButton(popupLabels[row][col]);
                optionButton.setFont(popupButtonFont);

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

    private void createPopupWithSub(JButton button, String[][] outerLabels, String[][][] innerLabels) {
        int rows = outerLabels.length;
        int cols = outerLabels[0].length;
        int index = 0;

        JPopupMenu popupMenu =  new JPopupMenu();

        JPanel gridPanel = new JPanel(new GridLayout(rows, cols, 5, 5));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JButton optionButton = new JButton(outerLabels[row][col]);
                optionButton.setFont(popupButtonFont);

                /* code for creating sub popup menu */
                String[][] subMenuLabels = innerLabels[index];
                int subrows = subMenuLabels.length;
                int subcols = subMenuLabels[0].length;

                JPopupMenu subPopupMenu =  new JPopupMenu();

                JPanel subGridPanel = new JPanel(new GridLayout(rows, cols, 5, 5));
                subGridPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                for (int _r = 0; _r < subrows; _r++) {
                    for (int _c = 0; _c < subcols; _c++) {
                        JButton subOptionButton = new JButton(subMenuLabels[_r][_c]);
                        subOptionButton.setFont(popupButtonFont);

                        subOptionButton.addActionListener(e -> {
                            if (listener != null) listener.onButtonClick(subOptionButton.getText());
                            subPopupMenu.setVisible(false);
                        });

                        subGridPanel.add(subOptionButton);
                    }
                }

                subPopupMenu.add(subGridPanel);
                /* end */

                optionButton.addActionListener(e -> {
                    subPopupMenu.show(button, 0, optionButton.getHeight());
                });

                gridPanel.add(optionButton);

                index += 1;
            }
        }

        popupMenu.add(gridPanel);

        button.addActionListener(e -> {
            popupMenu.show(button, 0, button.getHeight());
        });
    }


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
                button.setFont(buttonFont);

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
                // createPopupWithSub does the same, but also creates a popup menu for each option within the first popup menu
                //
                if (row == 0 && col == 0) createPopup(button, listPopupLabels);
                else if (row == 0 && col == 1) createPopup(button, matrixPopupLabels);
                else if (row == 0 && col == 2) createPopup(button, testPopupLabels);
                else if (row == 0 && col == 3) createPopup(button, solvePopupLabels);
                else if (row == 0 && col == 4) createPopup(button, graphPopupLabels);
                else if (row == 1 && col == 0) createPopupWithSub(button, statPopupLabels, statSubLabels);
                else if (row == 1 && col == 1) createPopupWithSub(button, numberPopupLabels, numberSubLabels);
                else if (row == 1 && col == 2) createPopup(button, calcPopupLabels);
                else if (row == 1 && col == 3) createPopup(button, specialPopupLabels);
                else if (row == 1 && col == 4) createPopup(button, cmplxPopupLabels);
                else if (row == 1 && col == 5) createPopup(button, constantsPopupLabels);

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
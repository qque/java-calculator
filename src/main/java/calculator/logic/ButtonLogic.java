/*
 *  Defines the parsing and behavior of buttons, including computation for "="
 *  Accepts any generic JTextArea
 *  
 *  NOTE: As this directly passes JS code to ScriptEngine, this code is unsafe.
 */

package calculator.logic;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import javax.script.ScriptException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import calculator.ui.ButtonPanel;

import calculator.History;
import calculator.Logger;
import calculator.Settings;

public class ButtonLogic implements ButtonPanel.ButtonListener {

    private static JTextArea display;

    private static History history = History.getHistory();

    private static Settings settings = Settings.getSettings();
    private static boolean DEBUG_MODE = settings.isDebugMode();

    private static Logger logger = Logger.getInstance();
    private static boolean DEBUG_LOG = settings.isDebugLog();

    public static void setTextArea(JTextArea area) { display = area; }


    private static final String[] numberButtons = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "ans"};

    // preprocessing using regex for more annoying conversions
    private static String preprocess(String expression) {
        if (DEBUG_MODE) {
            System.out.println("Preprocessing: " + expression);
        }
        if (DEBUG_LOG) {
            logger.log("Preprocessing: " + expression);
        }

        // replace limit to avoid infinite loop
        int safety = 0;
        final int SAFETY_LIM = 27;

        // check for user defined function
        // todo

        // replaces "ans" with answer value
        if (!history.isEmpty() && expression.contains("ans")) {
            expression = expression.replaceAll("ans", history.getLatest().get(1));

            if (DEBUG_LOG) {
                logger.log("(Accessed output of last calculation from history)");
            }
        }

        // converts "|xyz|" -> "abs(xyz)"
        while (expression.contains("|") && ++safety < SAFETY_LIM) {
            expression = expression.replaceAll("\\|([^|]+)\\|", "abs($1)");
        }

        // converts "(abc)^(xyz)" -> "(abc) ** (xyz)"
        expression = expression.replaceAll("\\^", "**");

        expression = expression.replaceAll("->", "=");

        // converts "x!" -> "fact(x)"
        while (expression.contains("!") && ++safety < SAFETY_LIM) {
            int i = expression.indexOf('!');
            int start = i - 1;
            if (start >= 0 && expression.charAt(start) == ')') {
                int d = 0, j = start;
                while (j >= 0) {
                    if (expression.charAt(j) == ')') d++;
                    else if (expression.charAt(j) == '(' && --d == 0) break;
                    j--;
                }
                expression = expression.substring(0, j) + "fact(" + expression.substring(j, i) + ")" + expression.substring(i + 1);
            } else {
                int j = start;
                while (j >= 0 && (Character.isLetterOrDigit(expression.charAt(j)) || expression.charAt(j) == '_')) j--;
                expression = expression.substring(0, j + 1) + "fact(" + expression.substring(j + 1, i) + ")" + expression.substring(i + 1);
            }
        }

        // converts "π" -> "pi"
        expression = expression.replaceAll("π", "pi");

        // check if degree mode is on. if so, use degree versions of math functions
        // patterns with negative lookbehind are making sure inverse versions dont double convert inverse trig functions
        if (ButtonPanel.isDegree) {
            expression = expression.replaceAll("(?<!a)sin", "dsin");
            expression = expression.replaceAll("(?<!a)cos", "dcos");
            expression = expression.replaceAll("(?<!a)tan", "dtan");
            expression = expression.replaceAll("asin", "dasin");
            expression = expression.replaceAll("acos", "dacos");
            expression = expression.replaceAll("atan", "datan");
            expression = expression.replaceAll("(?<!a)csc", "dcsc");
            expression = expression.replaceAll("(?<!a)sec", "dsec");
            expression = expression.replaceAll("(?<!a)cot", "dcot");
            expression = expression.replaceAll("acsc", "dacsc");
            expression = expression.replaceAll("asec", "dasec");
            expression = expression.replaceAll("acot", "dacot");
            expression = expression.replaceAll("(?<!a)sinh", "dsinh");
            expression = expression.replaceAll("(?<!a)cosh", "dcosh");
            expression = expression.replaceAll("(?<!a)tanh", "dtanh");
            expression = expression.replaceAll("asinh", "dasinh");
            expression = expression.replaceAll("acosh", "dacosh");
            expression = expression.replaceAll("atanh", "datanh");
        }
        
        if (DEBUG_MODE) {
            // fail message if preprocessing stalled on factorial or absolute value
            System.out.println((safety < SAFETY_LIM) ? "Processed: " + expression : "Preprocessing failed, moving to computation");
        }
        if (DEBUG_LOG) {
            logger.log((safety < SAFETY_LIM) ? "Processed:     " + expression : "Preprocessing failed, moving to computation");
        }

        return expression;
    }

    // defines parsing and computation for "=" button
    // uses generic Output type to handle double, int, boolean, etc.
    public static Output compute(String expression) throws ScriptException {
        expression = preprocess(expression);

        String eval = Engine.getValue(expression).toString();

        if (DEBUG_MODE) {
            System.out.println("Evaluated: " + eval);
        }
        if (DEBUG_LOG) {
            logger.log("Evaluated:     " + eval);
        }

        Output output;

        if (eval == null) {
            output = new Output("", Void.class);
        } else if (eval == "false" || eval == "true") {
            output = new Output(Boolean.parseBoolean(eval), Boolean.class);
        } else {
            if (eval == "NaN") {
                return new Output(Double.NaN, Double.class);
            } else if (eval == "Infinity") {
                return new Output("∞", String.class);
            } else if (eval == "-Infinity") {
                return new Output("-∞", String.class);
            } else {
                double result = Double.valueOf(eval);

                // note that, if the result was an integer before rounding (e.g. evaluating "2 + 2"), it will display as an int (e.g. "4")
                // if it was rounded (e.g. evaluating "erf(5)"), it will display as a double (e.g. "1.0")
                if ((long)result == result) {
                    return new Output((long)result, Long.class);
                }
                
                // check if floating point messed up a decimal, rounds up if so.
                // unfortunately, this gets rid of valid precision in cases where the result is close to an integer (e.g. "erf(5)")
                BigDecimal bd = new BigDecimal(Double.toString(result));
                bd = bd.setScale(4, RoundingMode.HALF_UP);
                double roundedResult = bd.doubleValue();

                if (Math.abs(result - roundedResult) < 0.000000001) {
                    result = roundedResult;
                }

                output = new Output(result, Double.class);
            }
        }

        return output;
    }

    private static boolean resultDisplayed = false;

    // static implementation of onButtonClick behavior
    public static void runButton(String label) {
        try {
            String output = null; // text output

            String result = null; // used only for calculation ("="), but it's defined in this larger scope
                                  // because it is also needed for some debug messages

            // check if result should be cleared based on input
            // most buttons will erase the display completely, some will truncate it into "ans"
            if (resultDisplayed) {
                if (label == "+" || label == "-" ||
                    label == "×" || label == "÷" ||
                    label == "^" || label == "x²" ||
                    label == "eˣ" || label == "10ˣ") {
                    display.setText("ans");
                } else {
                    display.setText("");
                }
            }

            String expression = display.getText();

            // set resultDisplayed & display color back accordingly
            resultDisplayed = false;
            display.setForeground(Color.BLACK);

            // switch label
            switch (label) {
            case "=":
                result = compute(expression).toString();
                display.setText(result);
                resultDisplayed = true;
                display.setForeground(Color.RED);
                history.add(expression, result);
                break;
            case "clear":
                display.setText(null);
                break;
            case "delete":
                int caretPosition = display.getCaretPosition();
                if (caretPosition > 0) {
                    display.getDocument().remove(caretPosition - 1, 1);
                }
                break;

            // do nothing
            case "(hyp.)":
            case "(rec.)":
            case "(deg.)":
            case "(rad.)":
            // for degenerate keyboard input
            case "null":
            case "^H":
            case "^N":
            case "^S":
            case "^D":
            // popup buttons (menu opening is dealt with before coming here)
            case "list":
            case "matrix":
            case "test":
            case "solve":
            case "graph":
            case "stat":
            case "calc":
            case "signal":
            case "special":
            case "cmplx":
            case "MENUS": break;
            
            // input modified label
            case "×": output = "*"; break;
            case "÷": output = "/"; break;
            case "|x|": output = "|"; break;
            case "x²": output = "^2"; break;
            case "10ˣ": output = "10^"; break;
            case "eˣ": output = "e^"; break;
            case "sin⁻¹": output = "asin("; break;
            case "cos⁻¹": output = "acos("; break;
            case "tan⁻¹": output = "atan("; break;
            case "csc⁻¹": output = "acsc("; break;
            case "sec⁻¹": output = "asec("; break;
            case "cot⁻¹": output = "acot("; break;
            case "sinh⁻¹": output = "asinh("; break;
            case "cosh⁻¹": output = "acosh("; break;
            case "tanh⁻¹": output = "atanh("; break;
            case "√": output = "sqrt("; break;
            case "1/x": output = "1/("; break;
            
            // input label with parenthesis
            case "sin":
            case "cos":
            case "tan":
            case "csc":
            case "sec":
            case "cot":
            case "sinh":
            case "cosh":
            case "tanh":
            case "ln":
            case "log":
                output = label + "(";
                break;

            // input plain label
            default:
                output = label;
                break;

            
            // buttons in popup menu
            //...

            }

            if (output != null) {
                display.setText(expression + output);
            }

            if (DEBUG_MODE || DEBUG_LOG) {
                String dout;

                if (label == "clear") {
                    dout = "";
                } else if (label == "delete") {
                    dout = expression.substring(0, expression.length()-1);
                } else if (label == "=") {
                    dout = expression + "=" + result;
                } else if (output == null) {
                    dout = expression + label;
                } else {
                    dout = expression + output;
                }

                if (DEBUG_MODE) System.out.println(label + ";    " + dout);
                if (DEBUG_LOG) logger.log(label + ";    " + dout);
            }

            // makes display scroll down so new text is visible
            display.setCaretPosition(display.getDocument().getLength());
        } catch (StringIndexOutOfBoundsException e) {
            // if user tries to delete with nothing, just ignore and move on
            return;
        } catch (Exception e) {
            System.out.println(e);

            // show generic error message to user
            // this is done since failure here should be the fault of the user
            JOptionPane.showMessageDialog(
                display,
                "An unexpected error occurred. Please check the syntax of your expression.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    public void onButtonClick(String label) {
        runButton(label);
    }

}

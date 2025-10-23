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

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import calculator.ui.ButtonPanel;
import calculator.History;

import calculator.Settings;

public class ButtonLogic implements ButtonPanel.ButtonListener {

    private static JTextArea display;

    private static History history = History.getInstance();

    public static void setTextArea(JTextArea area) { display = area; }

    // preprocessing using regex for more annoying conversions
    private static String preprocess(String expression) {
        if (Settings.DEBUG_MODE) {
            System.out.println("Preprocessing: " + expression);
        }

        int safety = 0;

        // check for user defined function

        // replaces "ans" with answer value
        if (!history.isEmpty()) {
            if (Settings.DEBUG_MODE) System.out.println("Accessed output from history");
            expression = expression.replaceAll("ans", history.getLatest().get(1));
        }

        // converts "|xyz|" -> "Math.abs(xyz)"
        while (expression.contains("|") && ++safety < 20) {
            expression = expression.replaceAll("\\|([^|]+)\\|", "abs($1)");
        }

        // converts "(abc)^(xyz)" -> "(abc) ** (xyz)"
        expression = expression.replaceAll("\\^", "**");

        expression = expression.replaceAll("->", "=");

        // converts "x!" -> "fact(x)"
        while (expression.contains("!") && ++safety < 20) {
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

        // converts "π" & "pi" -> "Math.PI" and "e" -> "Math.E"
        expression = expression.replaceAll("π", "Math.PI");
        expression = expression.replaceAll("pi", "Math.PI");
        expression = expression.replaceAll("(?<![a-zA-Z0-9_])e(?![a-zA-Z0-9_])", "Math.E");

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
        
        if (Settings.DEBUG_MODE) {
            // fail message if preprocessing stalled on factorial or absolute value
            System.out.println((safety < 20) ? "Processed: " + expression : "Preprocessing failed, moving to computation");
        }

        return expression;
    }

    private static ScriptEngineManager manager;
    private static ScriptEngine engine;

    public static boolean isSetup = false; 

    // setup javascript ScriptEngine to evaluate, done on initialization 
    public static void setupEngine() throws ScriptException {
        manager = new ScriptEngineManager();
        engine = manager.getEngineByName("graal.js");

        if (engine == null) {
            throw new ScriptException("graal.js engine not found. See if the dependency is properly included in the build configuration");
        }

        // functions that need to be defined for computation (stdev, nCr, etc.)
        // see ../../../resources/functionDefinitions.js for a readable version of the code with comments
        try {
            String content;

            // default value is false for both. if either is true, 
            if (Settings.USE_CUSTOM_FUNCTION_FILE) {
                String path = Settings.CUSTOM_FUNCTION_FILE;
                if (path != null) {
                    Scanner customFileScanner = new Scanner(new File(path), StandardCharsets.UTF_8).useDelimiter("\\Z");
                    content = customFileScanner.next();
                    customFileScanner.close();
                } else {
                    throw new IOException("Custom function file not defined, or specified path not found");
                }
            } else {
                File minifiedJS = new File("src/main/resources/minified.js");
                if (minifiedJS.exists()) {
                    Scanner minifiedJSScanner = new Scanner(minifiedJS, StandardCharsets.UTF_8).useDelimiter("\\Z");
                    content = minifiedJSScanner.next();

                    // if 
                    if (!Settings.LOAD_ADVANCED) content = content.substring(content.indexOf("###*/"));

                    minifiedJSScanner.close();
                } else {
                    throw new IOException("minified.js not found, try running ./util/minify");
                }

                if (Settings.ADD_CUSTOM_FUNCTION_FILE) {
                    String path = Settings.CUSTOM_FUNCTION_FILE;
                    if (path != null) {
                        Scanner customFileScanner = new Scanner(new File(path), StandardCharsets.UTF_8).useDelimiter("\\Z");
                        content += customFileScanner.next();
                        customFileScanner.close();
                    } else {
                        throw new IOException("Custom function file not defined, or specified path not found");
                    }
                }
            }

            engine.eval(content);
        } catch (ScriptException | IOException e) {
            // critical exception, must exit program 
            System.out.println(e);
            System.exit(0);
        } catch (Exception e) {
            System.out.println(e);
        }

        isSetup = true;
    }

    // string evaluator for debug console
    public static Object engineEval(String code) {
        try {
            return engine.eval(code);
        } catch (ScriptException e) {
            System.out.println(e);
            return 0;
        }
    }

    // defines parsing and computation for "=" button
    // uses generic Output type to handle double, int, boolean, etc.
    public static Output compute(String expression) throws ScriptException {
        expression = preprocess(expression);

        String eval = engine.eval(expression).toString();

        if (Settings.DEBUG_MODE) {
            System.out.println("Evaluated: " + eval);
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

        if (Settings.DEBUG_MODE) {
            System.out.println(expression + "=" + output.toString());
        }

        return output;
    }

    // static implementation of onButtonClick behavior
    public static void runButton(String label) { 
        if (!isSetup) {
            try {
                setupEngine();
            } catch (ScriptException e) {
                System.out.println(e);
                System.exit(1);
            }
        }

        try {
            String expression = display.getText();
            String output = null;

            if (label != "=") display.setForeground(Color.BLACK);

            if (Settings.DEBUG_MODE) {
                System.out.println(label + ";    " + expression);
            }

            switch (label) {
            case "=":
                String result = compute(expression).toString();
                display.setText(result);
                display.setForeground(Color.RED);
                System.out.println(expression + ",   " + result);
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
            case "1/x": output = "1/"; break;
            case "|x|": output = "|"; break;
            case "x²": output = "^2"; break;
            case "10ˣ": output = "10^"; break;
            case "eˣ": output = "e^"; break;
            
            // input label with parenthesis (inverse trig & sqrt have modified label as well)
            case "sin": output = "sin("; break;
            case "cos": output = "cos("; break;
            case "tan": output = "tan("; break;
            case "sin⁻¹": output = "asin("; break;
            case "cos⁻¹": output = "acos("; break;
            case "tan⁻¹": output = "atan("; break;
            case "csc": output = "csc("; break;
            case "sec": output = "sec("; break;
            case "cot": output = "cot("; break;
            case "csc⁻¹": output = "acsc("; break;
            case "sec⁻¹": output = "asec("; break;
            case "cot⁻¹": output = "acot("; break;
            case "sinh": output = "sinh("; break;
            case "cosh": output = "cosh("; break;
            case "tanh": output = "tanh("; break;
            case "sinh⁻¹": output = "asinh("; break;
            case "cosh⁻¹": output = "acosh("; break;
            case "tanh⁻¹": output = "atanh("; break;
            case "√": output = "sqrt("; break;
            case "log": output = "log("; break;
            case "ln": output = "ln("; break;
            case "nPr": output = "nPr("; break;
            case "nCr": output = "nCr("; break;
            case "∫": output = "∫("; break;

            // input plain label (includes `ans`, which is converted in preprocessing)
            default:
                display.setText(expression + label);
                break;


            // buttons in popup menu
            //...
            }

            if (output != null) {
                display.setText(expression + output);
            }

            // makes display scroll down so new text is visible
            display.setCaretPosition(display.getDocument().getLength());
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

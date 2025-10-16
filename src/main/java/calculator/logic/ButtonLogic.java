/*
 *  Defines the parsing and behavior of buttons, including computation for "="
 *  Accepts any generic JTextArea using the setTextArea method
 */

package calculator.logic;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import calculator.ui.ButtonPanel;
import calculator.History;

public class ButtonLogic implements ButtonPanel.ButtonListener {

    private static final int DEBUG_MODE = 1;

    private static JTextArea display;

    private static History history = History.getInstance();

    public static void setTextArea(JTextArea area) { display = area; }

    // preprocessing using regex for more annoying conversions
    private static String preprocess(String expression) {
        if (DEBUG_MODE == 1) {
            System.out.println("Preprocessing: " + expression);
        }

        // replaces "ans" with answer value
        if (!history.isEmpty()) expression = expression.replaceAll("ans", history.getLatest().get(1));

        // converts "|xyz|" -> "Math.abs(xyz)"
        while (expression.contains("|")) {
            expression = expression.replaceAll("\\|([^|]+)\\|", "Math.abs($1)");
        }

        // converts "(abc)^(xyz)" -> "(abc) ** (xyz)"
        expression = expression.replaceAll("\\^", "**");

        // converts "x!" -> "fact(x)"
        if (expression.contains("!")) expression = expression.replaceAll("(\\([^()]+\\)|[\\w.]+)!", "fact($1)");

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
        
        if (DEBUG_MODE == 1) {
            System.out.println("Processed: " + expression);
        }

        return expression;
    }

    private static ScriptEngineManager manager;
    private static ScriptEngine engine;

    public static boolean isSetup = false; 

    // setup javascript ScriptEngine to evaluate, done on initialization 
    public static void setupEngine() {
        manager = new ScriptEngineManager();
        engine = manager.getEngineByName("graal.js");

        // functions that need to be defined for computation (stdev, nCr, etc.)
        // see ../../../resources/functionDefinitions.js for a readable version of the code with comments
        try {
            engine.eval("function sin(t){return Math.sin(t)}function cos(t){return Math.cos(t)}function tan(t){return Math.tan(t)}" +
            "function asin(t){return Math.asin(t)}function acos(t){return Math.acos(t)}function atan(t){return Math.atan(t)}" +
            "function sinh(t){return Math.sinh(t)}function cosh(t){return Math.cosh(t)}function tanh(t){return Math.tanh(t)}" +
            "function asinh(t){return Math.asinh(t)}function acosh(t){return Math.acosh(t)}function atanh(t){return Math.atanh(t)}" +
            "function sqrt(t){return Math.sqrt(t)}function ln(t){return Math.log(t)}function csc(t){return 1/Math.sin(t)}" +
            "function sec(t){return 1/Math.cos(t)}function cot(t){return 1/Math.tan(t)}function acsc(t){return Math.asin(1/t)}" +
            "function asec(t){return Math.acos(1/t)}function acot(t){return Math.PI-Math.atan(t)}function log(t,n){return Math.log(n)/Math.log(t)}" +
            "function dsin(t){return t=Math.PI*t/180,Math.sin(t)}function dcos(t){return t=Math.PI*t/180,Math.cos(t)}" +
            "function dtan(t){return t=Math.PI*t/180,Math.tan(t)}function dasin(t){return t=Math.PI*t/180,Math.asin(t)}" +
            "function dacos(t){return t=Math.PI*t/180,Math.acos(t)}function datan(t){return t=Math.PI*t/180,Math.atan(t)}" +
            "function dsinh(t){return t=Math.PI*t/180,Math.sinh(t)}function dcosh(t){return t=Math.PI*t/180,Math.cosh(t)}" +
            "function dtanh(t){return t=Math.PI*t/180,Math.tanh(t)}function dasinh(t){return t=Math.PI*t/180,Math.asinh(t)}" +
            "function dacosh(t){return t=Math.PI*t/180,Math.acosh(t)}function datanh(t){return t=Math.PI*t/180,Math.atanh(t)}" +
            "function dcsc(t){return t=Math.PI*t/180,1/Math.sin(t)}function dsec(t){return t=Math.PI*t/180,1/Math.cos(t)}" +
            "function dcot(t){return t=Math.PI*t/180,1/Math.tan(t)}function dacsc(t){return t=Math.PI*t/180,Math.asin(1/t)}" +
            "function dasec(t){return t=Math.PI*t/180,Math.acos(1/t)}function dacot(t){return t=Math.PI*t/180,Math.PI-Math.atan(t)}" +
            "function mean(...t){return t.reduce(((t,n)=>t+n),0)/t.length}function stdev(...t){for(var n=0,a=mean(...t),r=t.length,h=0,u=0;u<r;u++)n+=(h=t[u]-a)*h;return n/=r,Math.sqrt(n)}" +
            "function stdevp(...t){for(var n=0,a=mean(...t),r=t.length,h=0,u=0;u<r;u++)n+=(h=t[u]-a)*h;return n/=r-1,Math.sqrt(n)}" +
            "function erf(t){const n=Math.sign(t),a=Math.abs(t),e=1/(1+.3275911*a);return n*(1-((((1.061405429*e-1.453152027)*e+1.421413741)*e-.284496736)*e+.254829592)*e*Math.exp(-a*a))}" +
            "function gamma(x){const a=[676.5203681218851,-1259.1392167224028,771.3234287776531,-176.6150291621406,12.507343278686905,-.13857109526572012,9984369578019572e-21,1.5056327351493116e-7];if(x<.5)return Math.PI/(Math.sin(Math.PI*x)*gamma(1-x));{x-=1;let t=.9999999999998099;for(let e=0;e<a.length;e++)t+=a[e]/(x+e+1);const e=x+a.length-.5;return Math.sqrt(2*Math.PI)*Math.pow(e,x+.5)*Math.exp(-e)*t}}" +
            "function fact(t){if(Number.isInteger(t)){for(var a=1,e=2;e<=t;e++)a*=e;return a}return gamma(t+1)}" +
            "function nPr(t,n){for(var a=1,r=t-n+1;r<=t;r++)a*=r;return a}function nCr(t,n){for(var a=1,r=t-n+1;r<=t;r++)a*=r,a/=t+1-r;return a};");
        } catch (ScriptException e) {
            System.out.println(e);
            System.exit(0);
        }

        isSetup = true;
    }

    // defines parsing and computation for "=" button
    public static double compute(String expression) throws ScriptException {
        expression = preprocess(expression);

        double result = Double.parseDouble(engine.eval(expression).toString());

        // check if floating point messed up a decimal
        BigDecimal bd = new BigDecimal(Double.toString(result));
        bd = bd.setScale(4, RoundingMode.HALF_UP);
        double roundedResult = bd.doubleValue();

        if (Math.abs(result - roundedResult) < 0.00000001) result = roundedResult;

        if (DEBUG_MODE == 1) {
            System.out.println(expression + "=" + result);
        }

        return result;
    }

    // static implementation of onButtonClick behavior
    public static void runButton(String label) { 
        if (!isSetup) setupEngine();

        try {
            String expression = display.getText();
            String output = null;

            if (DEBUG_MODE == 1) {
                System.out.println(label + ";    " + expression);
            }

            switch (label) {
                case "clear":
                    display.setText(null);
                    break;
                case "delete":
                    int caretPosition = display.getCaretPosition();
                    if (caretPosition > 0) {
                        display.getDocument().remove(caretPosition - 1, 1);
                    }
                    break;
                case "ans":
                    // `ans` will display "ans" to the user, and is dealt with in parsing
                    display.setText(expression + label);
                    break;
                case "=":
                    String result = compute(expression) + "";
                    display.setText(result);
                    history.add(expression, result);
                    break;

                // input nothing
                case "(hyp.)":
                case "(rec.)":
                case "(deg.)":
                case "(rad.)": break;
                
                // input with modified symbol
                case "×": output = "*"; break;
                case "÷": output = "/"; break;
                case "1/x": output = "1/"; break;
                case "|x|": output = "|"; break;
                case "x²": output = "^2"; break;
                case "10ˣ": output = "10^"; break;
                case "eˣ": output = "e^"; break;
                
                // input with parenthesis (inverse trig & sqrt have modified symbol as well)
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
                case "mean": output = "mean("; break;
                case "stdev": output = "stdev("; break;
                case "stdevp": output = "stdevp("; break;
                case "erf": output = "erf("; break;

                // input normally
                default:
                    display.setText(expression + label);
                    break;
            }

            if (output != null) {
                display.setText(expression + output);
            }
        } catch (Exception e) {
            System.out.println(e);

            // show generic error message to user
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

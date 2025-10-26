/*
 *  Defines the JEP interpreter and related functions used for evaluation within the calculator.
 *  On static initialization, the functions defined in resources/functions.py will be loaded
 */

package calculator.logic;

import jep.SharedInterpreter;
import jep.JepException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import calculator.Settings;

public class Engine {

    private static Settings settings = Settings.getSettings();

    @SuppressWarnings("resource")
    private static final SharedInterpreter pyi = new SharedInterpreter();

    // first, set and load jep dll
    // then, load functions.py with jep and evaluate
    static {
        String content;

        try {
            String pythonHome = findPythonHome(findPythonExe());
            String jepPath = pythonHome + "\\jep";

            System.setProperty("python.home", pythonHome);

            File dll = new File(jepPath, "jep.dll");
            if (!dll.exists()) {
                throw new RuntimeException("Could not find jep.dll at: " + dll.getAbsolutePath());
            }

            System.load(dll.getAbsolutePath());

            // load calculation settings to be used in internals
            pyi.eval("precision = " + settings.getPrecision());

            if (settings.getUseCustomFunctionFile()) {
                String path = settings.getCustomFunctionFile();
                if (path != null) {
                    Scanner customFileScanner = new Scanner(new File(path), StandardCharsets.UTF_8).useDelimiter("\\Z");
                    content = customFileScanner.next();
                    customFileScanner.close();
                } else {
                    throw new IOException("Custom function file not defined, or specified path not found");
                }
            } else {
                File minifiedPython = new File("src/main/resources/minified.py");
                if (minifiedPython.exists()) {
                    Scanner minifiedPythonScanner = new Scanner(minifiedPython, StandardCharsets.UTF_8).useDelimiter("\\Z");
                    content = minifiedPythonScanner.next();

                    // if user doesn't want advanced functions, cut that part off
                    if (!settings.getLoadAdvanced()) content = content.substring(content.indexOf("\"eof\"") + 5);

                    minifiedPythonScanner.close();
                } else {
                    throw new IOException("minified.py not found, try running ./util/minify");
                }

                if (settings.getAddCustomFunctionFile()) {
                    String path = settings.getCustomFunctionFile();
                    if (path != null) {
                        Scanner customFileScanner = new Scanner(new File(path), StandardCharsets.UTF_8).useDelimiter("\\Z");
                        content += customFileScanner.next();
                        customFileScanner.close();
                    } else {
                        throw new IOException("Custom function file not defined, or specified path not found");
                    }
                }
            }
            
            pyi.exec(content);
        } catch (IOException e) {
            System.out.println(e);
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.exit(1);
        } catch (JepException e) {
            if (settings.getDebugMode()) System.out.println(e);
            System.out.println("Error occurred in JEP function evaluation. NumPy, SymPy, mpmath, or gmpy2 may not have been installed.");
            System.out.println("The calculator will still load, but core functions may not run correctly.");
        } catch (RuntimeException e) {
            System.out.println(e);
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.exit(1);
        } 

    }

    // attempts to find python installation
    private static String findPythonExe() {
        String[] possible = {
            System.getenv("PYTHON_HOME"),
            "C:\\Python313",
            "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Programs\\Python\\Python313",
            "C:\\Program Files\\Python313",
            "C:\\Program Files (x86)\\Python313",
            "C:\\Python312",
            "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Programs\\Python\\Python312",
            "C:\\Program Files\\Python312",
            "C:\\Program Files (x86)\\Python312",
            "C:\\Python311",
            "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Programs\\Python\\Python311",
            "C:\\Program Files\\Python311",
            "C:\\Program Files (x86)\\Python311",
            "C:\\Python310",
            "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Programs\\Python\\Python310",
            "C:\\Program Files\\Python310",
            "C:\\Program Files (x86)\\Python310"
        };

        for (String path : possible) {
            if (path != null && new File(path, "python.exe").exists()) {
                return path + "\\python.exe";
            }
        }

        throw new RuntimeException("Python installation not found. Please install Python or set PYTHON_HOME.");
    }

    // attempts to find python installation
    public static String findPythonHome(String pythonExePath) {
        ProcessBuilder pb = new ProcessBuilder(pythonExePath, "-m", "site", "--user-site");
        pb.redirectErrorStream(true);

        try {
            Process process = pb.start();
            String line;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                line = reader.readLine();
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Python process exited with code " + exitCode);
                return null;
            }

            if (line != null && !line.isEmpty()) {
                return line.trim();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getPythonVersion(String pythonExePath) {
        try {
            Process process = new ProcessBuilder(pythonExePath, "--version")
                .redirectErrorStream(true) // handles stderr (since older Pythons print version to stderr)
                .start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line = reader.readLine();
                if (line != null && line.startsWith("Python ")) {
                    return line.substring(7).trim(); // e.g. "3.13.0"
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private Engine() {}

    public static void exec(String code) {
        pyi.exec(code);
    }

    public static void eval(String code) {
        pyi.eval(code);
    }

    public static Object getValue(String expression) {
        return pyi.getValue(expression);
    }

    public static void close() {
        pyi.close();
    }
}
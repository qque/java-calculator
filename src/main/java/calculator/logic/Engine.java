/*
 *  Defines the JEP interpreter and related functions used for evaluation within the calculator.
 *  On static initialization, the functions defined in resources/functions.py will be loaded
 */

package calculator.logic;

import jep.SharedInterpreter;
import jep.JepException;

import java.lang.reflect.Field;

import java.io.File;
import java.io.IOException;
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
            String pythonHome = findPythonHome();
            String jepPath = pythonHome + "\\Lib\\site-packages\\jep";

            System.setProperty("python.home", pythonHome);

            addLibraryPath(jepPath);

            System.loadLibrary("jep");

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

                    // if user doesn't want want advanced functions, cut off the bulk of file
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
            
            pyi.eval(content);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        } catch (JepException e) {
            System.out.println("Error occurred in JEP function evaluation. Numpy, Sympy, or Mpmath may not have been installed.");
            System.out.println("The calculator will still load, but core functions may not run correctly.");
        } catch (RuntimeException | IllegalAccessException | NoSuchFieldException e) {
            System.out.println(e);
            System.exit(1);
        } 

    }

    // attempts to find python installation
    private static String findPythonHome() {
        String[] possible = {
            System.getenv("PYTHON_HOME"),
            "C:\\Python313",
            "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Programs\\Python\\Python313",
            "C:\\Program Files\\Python313",
            "C:\\Program Files (x86)\\Python313"
        };

        for (String path : possible) {
            if (path != null && new File(path, "python.exe").exists()) {
                return path;
            }
        }

        throw new RuntimeException("Python installation not found. Please install Python 3.13 or set PYTHON_HOME.");
    }

    private static void addLibraryPath(String pathToAdd) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
        usrPathsField.setAccessible(true);

        String[] paths = (String[]) usrPathsField.get(null);
        for (String path : paths) {
            if (path.equals(pathToAdd)) return;
        }

        String[] newPaths = new String[paths.length + 1];
        System.arraycopy(paths, 0, newPaths, 0, paths.length);
        newPaths[paths.length] = pathToAdd;
        usrPathsField.set(null, newPaths);
    }

    private Engine() {}

    public static Object eval(String code) {
        pyi.eval(code);
        return null;
    }

    public static Object getValue(String expression) {
        return pyi.getValue(expression);
    }

    public static void close() {
        pyi.close();
    }
}
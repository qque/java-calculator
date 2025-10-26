/* 
 *  Defines creation of log files in calculator/logs/ and writing to log files
 *  
 *  The log is generally equivalent to the console output, with the main differences
 *  being that the log file attaches a timestamps to each line and aligns output.
 *  
 *  The log file will also be a bit more verbose in certain cases where more information
 *  can be provided, such as with errors or meta-operations (like opening a new frame).
 */

package calculator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static Logger instance;
    private static final Object lock = new Object();
    private final BufferedWriter writer;

    // format: logs/2025-10-26_9-45-07.log
    private static final DateTimeFormatter FILE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private static final DateTimeFormatter LOG_FORMAT = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss");

    private Logger() throws IOException {
        Path logDir = Path.of(System.getProperty("user.dir"), "logs");
        if (!Files.exists(logDir)) {
            Files.createDirectories(logDir);
        }

        String fileName = LocalDateTime.now().format(FILE_FORMAT) + ".log";
        Path logFile = logDir.resolve(fileName);

        writer = new BufferedWriter(new FileWriter(logFile.toFile(), true));
    }

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    try {
                        instance = new Logger();
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to initialize Logger", e);
                    }
                }
            }
        }
        return instance;
    }

    public void log(String message) {
        synchronized (lock) {
            try {
                String timestamp = LocalDateTime.now().format(LOG_FORMAT);

                String formattedMessage = alignMessage(message);
                writer.write("[" + timestamp + "] " + formattedMessage);

                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                System.out.println("Failed to write log: " + e.getMessage());
            }
        }
    }

    public void close() {
        synchronized (lock) {
            try {
                writer.close();
            } catch (IOException e) {
                System.out.println("Failed to close logger: " + e.getMessage());
            }
        }
    }

    private String alignMessage(String message) {
        String[] parts = message.split(";", 2);
        if (parts.length == 2) {
            String label = parts[0].trim();
            String content = parts[1].trim();
            return String.format("%-14s %s", label + ";", content);
        } else {
            return message;
        }
    }

}
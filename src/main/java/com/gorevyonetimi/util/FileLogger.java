package com.gorevyonetimi.util;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {

    private static final String LOG_FILE = "logs/log.txt";

    public static void log(String message) {
        try {
            Files.createDirectories(Path.of("logs"));
            FileWriter writer = new FileWriter(LOG_FILE, true);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("[" + timestamp + "] " + message + "\n");
            writer.close();
        } catch (IOException e) {
            System.err.println("Log yazılamadı: " + e.getMessage());
        }
    }
}

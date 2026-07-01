package com.clinicops;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AuditLogger {

    public enum Level { INFO, WARNING, ERROR }

    private static final List<String> logs = new ArrayList<>();
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private AuditLogger() {
    }

    public static void log(String message, Level level) {
        String timestamp = LocalDateTime.now().format(formatter);
        String entry = String.format("[%s] [%-7s] %s", timestamp, level.name(), message);
        logs.add(entry);
    }

    public static List<String> getLogs() {
        return logs;
    }
}
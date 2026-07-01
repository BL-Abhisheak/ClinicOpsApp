package com.clinicops;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AuditLogger {

    public enum Level { INFO, WARNING, ERROR }

    private static final Logger logger = LogManager.getLogger(AuditLogger.class);

    private static final List<String> logs = new ArrayList<>();

    private AuditLogger() {
    }

    public static void log(String message, Level level) {
        logs.add("[" + level.name() + "] " + message);

        switch (level) {
            case INFO:
                logger.info(message);
                break;
            case WARNING:
                logger.warn(message);
                break;
            case ERROR:
                logger.error(message);
                break;
        }
    }

    public static List<String> getLogs() {
        return logs;
    }
}
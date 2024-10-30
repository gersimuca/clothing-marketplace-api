package com.gersimuca.cm.common.util;

import com.gersimuca.cm.common.exception.ErrorSeverity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;

/** Utility class for logging various levels of messages and exceptions. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoggerUtil {

    /**
     * Logs an exception with a specified error severity and web request details.
     *
     * @param logger the logger to use
     * @param exception the exception to log
     * @param errorSeverity the severity of the error
     * @param requestDetails additional details about the web request
     */
    public static void exception(
            Logger logger, Exception exception, ErrorSeverity errorSeverity, String requestDetails) {
        final String message = "Exception occurred: {}, Request Details: {}";
        switch (errorSeverity) {
            case CRITICAL -> error(logger, message, exception.getMessage(), requestDetails, exception);
            case WARN -> warn(logger, message, exception.getMessage(), requestDetails);
            case INFO -> info(logger, message, exception.getMessage(), requestDetails);
        }
    }
    public static void info(Logger logger, String message, Object... args) {
        logger.info(message, args);
    }
    public static void warn(Logger logger, String message, Object... args) {
        logger.warn(message, args);
    }
    public static void error(Logger logger, String message, Object... args) {
        logger.error(message, args);
    }
    public static void debug(Logger logger, String message, Object... args) {
        logger.debug(message, args);
    }
}


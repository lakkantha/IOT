package com.iot.service.processing;

/**
 * Simple result object for processing stages.
 */
public class ProcessingResult {
    private final boolean success;
    private final String message;

    private ProcessingResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static ProcessingResult ok() {
        return new ProcessingResult(true, null);
    }

    public static ProcessingResult fail(String message) {
        return new ProcessingResult(false, message);
    }

    public boolean success() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}

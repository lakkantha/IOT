package com.iot.config;

public class KafkaProcessingException extends RuntimeException {
    
    public KafkaProcessingException(String message) {
        super(message);
    }

    public KafkaProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public static KafkaProcessingException deviceNotFound(String deviceId) {
        return new KafkaProcessingException("Device not found with ID: " + deviceId);
    }

    public static KafkaProcessingException invalidData(String deviceId, String reason) {
        return new KafkaProcessingException(
            String.format("Invalid data received from device [%s]: %s", deviceId, reason));
    }
}
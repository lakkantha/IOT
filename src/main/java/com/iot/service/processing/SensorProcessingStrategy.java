package com.iot.service.processing;

import com.iot.model.dto.SensorDataDto;

/**
 * Strategy interface for processing sensor data per sensor type.
 */
public interface SensorProcessingStrategy {
    boolean supports(String sensorType);
    void process(SensorDataDto dto);
}

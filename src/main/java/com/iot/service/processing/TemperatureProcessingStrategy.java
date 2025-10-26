package com.iot.service.processing;

import com.iot.model.dto.SensorDataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TemperatureProcessingStrategy implements SensorProcessingStrategy {
    private static final Logger log = LoggerFactory.getLogger(TemperatureProcessingStrategy.class);

    @Override
    public boolean supports(String sensorType) {
        return "temperature".equalsIgnoreCase(sensorType);
    }

    @Override
    public void process(SensorDataDto dto) {
        if (dto == null) return;
        Map<String, String> measurements = dto.getMeasurements();
        if (measurements == null || measurements.isEmpty()) {
            log.debug("No measurements for device {}", dto.getDeviceId());
            return;
        }
        // example: log the temperature measurement if available
        String temp = measurements.get("temperature");
        if (temp != null) {
            log.info("Processing temperature {} for device {}", temp, dto.getDeviceId());
            // placeholder: add enrichment/validation/persistence
        }
    }
}

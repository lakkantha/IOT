package com.iot.service;

import com.iot.config.ApplicationConfig;
import com.iot.model.Device;
import com.iot.model.SensorData;
import com.iot.model.dto.SensorDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Map;

@Slf4j
@Service
public class SensorDataProcessingServiceImpl implements SensorDataProcessingService {

    private final ApplicationConfig config;

    public SensorDataProcessingServiceImpl() {
        this.config = ApplicationConfig.getInstance();
    }

    @Override
    public SensorData processSensorData(SensorDataDto sensorDataDto, Device device) {
        validateSensorData(sensorDataDto);
        
        // Log processing with application name from config
        log.info("[{}] Processing sensor data for device: {}", 
            config.getApplicationName(), device.getDeviceId());
            
        SensorData.DataQuality quality = assessDataQuality(sensorDataDto);
        SensorData sensorData = new SensorData();
        sensorData.setDevice(device);
        sensorData.setTimestamp(sensorDataDto.getTimestamp() != null ? sensorDataDto.getTimestamp() : Instant.now());
        sensorData.setMeasurements(sensorDataDto.getMeasurements());
        sensorData.setDataQuality(quality);
        
        // Use configuration for batch processing
        if (device.getSensorData().size() >= config.getDeviceBatchSize()) {
            log.info("Reached batch size limit ({}) for device: {}", 
                config.getDeviceBatchSize(), device.getDeviceId());
        }
        
        return sensorData;
    }

    @Override
    public void validateSensorData(SensorDataDto sensorDataDto) {
        if (sensorDataDto.getDeviceId() == null || sensorDataDto.getDeviceId().isEmpty()) {
            throw new IllegalArgumentException("Device ID is required");
        }
        if (sensorDataDto.getMeasurements() == null || sensorDataDto.getMeasurements().isEmpty()) {
            throw new IllegalArgumentException("Measurements are required");
        }
    }

    @Override
    public SensorData.DataQuality assessDataQuality(SensorDataDto sensorDataDto) {
        Map<String, String> measurements = sensorDataDto.getMeasurements();
        if (measurements == null || measurements.isEmpty()) {
            return SensorData.DataQuality.BAD;
        }
        // Simple rule: if any value is null or empty, mark as QUESTIONABLE
        for (String value : measurements.values()) {
            if (value == null || value.isEmpty()) {
                return SensorData.DataQuality.QUESTIONABLE;
            }
        }
        return SensorData.DataQuality.GOOD;
    }
}
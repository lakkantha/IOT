package com.iot.service;

import com.iot.model.Device;
import com.iot.model.SensorData;
import com.iot.model.dto.SensorDataDto;

public interface SensorDataProcessingService {
    SensorData processSensorData(SensorDataDto sensorDataDto, Device device);
    void validateSensorData(SensorDataDto sensorDataDto);
    SensorData.DataQuality assessDataQuality(SensorDataDto sensorDataDto);
}
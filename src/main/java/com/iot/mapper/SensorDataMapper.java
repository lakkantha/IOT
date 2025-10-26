package com.iot.mapper;

import com.iot.model.SensorData;
import com.iot.model.dto.SensorDataDto;

/**
 * Simple mapper for SensorDataDto -> SensorData. Device must be set by caller.
 */
public class SensorDataMapper {
    public static SensorData toEntity(SensorDataDto dto) {
        if (dto == null) return null;
        SensorData sd = new SensorData();
        sd.setTimestamp(dto.getTimestamp());
        if (dto.getMeasurements() != null) {
            sd.getMeasurements().putAll(dto.getMeasurements());
        }
        // dataQuality left unset by mapper (service should set)
        return sd;
    }
}

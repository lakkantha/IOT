package com.iot.model.dto;

import lombok.Data;
import java.time.Instant;
import java.util.Map;

@Data
public class SensorDataDto {
    private String deviceId;
    private Instant timestamp;
    private Map<String, String> measurements;
}
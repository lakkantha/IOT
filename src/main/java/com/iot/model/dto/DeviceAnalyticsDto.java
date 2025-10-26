package com.iot.model.dto;

import lombok.Data;
import java.util.Map;

@Data
public class DeviceAnalyticsDto {
    private String deviceId;
    private long totalMeasurements;
    private Map<String, Double> averageValues;
    private Map<String, Double> maxValues;
    private Map<String, Double> minValues;
    private double dataQualityScore;
}
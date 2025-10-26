package com.iot.service.processing;

import com.iot.model.SensorData;

/**
 * Single-stage processor in a processing pipeline.
 */
public interface DataProcessor {
    ProcessingResult process(SensorData data);
}

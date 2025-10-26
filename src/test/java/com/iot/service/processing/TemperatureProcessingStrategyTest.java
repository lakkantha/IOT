package com.iot.service.processing;

import com.iot.model.dto.SensorDataDto;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureProcessingStrategyTest {

    @Test
    void supports_and_process_work() {
        TemperatureProcessingStrategy strat = new TemperatureProcessingStrategy();
        assertTrue(strat.supports("temperature"));
        assertTrue(strat.supports("Temperature"));

        SensorDataDto dto = new SensorDataDto();
        dto.setDeviceId("dev-1");
        dto.setTimestamp(Instant.now());
        dto.setMeasurements(new HashMap<>());
        dto.getMeasurements().put("temperature", "25.0");

        // ensure no exception thrown
        strat.process(dto);
    }
}

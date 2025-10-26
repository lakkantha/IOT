package com.iot.mapper;

import com.iot.model.SensorData;
import com.iot.model.dto.SensorDataDto;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class SensorDataMapperTest {

    @Test
    void toEntity_transfersTimestampAndMeasurements() {
        SensorDataDto dto = new SensorDataDto();
        dto.setTimestamp(Instant.parse("2025-10-25T00:00:00Z"));
        HashMap<String, String> m = new HashMap<>();
        m.put("temperature", "22.5");
        dto.setMeasurements(m);

        SensorData sd = SensorDataMapper.toEntity(dto);

        assertNotNull(sd);
        assertEquals(dto.getTimestamp(), sd.getTimestamp());
        assertEquals("22.5", sd.getMeasurements().get("temperature"));
    }
}

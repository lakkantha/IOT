package com.iot.mapper;

import com.iot.model.Device;
import com.iot.model.common.Location;
import com.iot.model.dto.DeviceRegistrationDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeviceMapperTest {

    @Test
    void toEntity_mapsFields() {
        DeviceRegistrationDto dto = new DeviceRegistrationDto();
        dto.setDeviceId("dev-123");
        dto.setDeviceType("thermostat");
        dto.setFirmware("1.2.3");
        dto.setLocation(new Location(12.34, 56.78, "Test Address"));

        Device device = DeviceMapper.toEntity(dto);

        assertNotNull(device);
        assertEquals("dev-123", device.getDeviceId());
        assertEquals("thermostat", device.getDeviceType());
        assertEquals("1.2.3", device.getFirmware());
        assertNotNull(device.getLocation());
        assertEquals(12.34, device.getLocation().getLatitude());
    }
}

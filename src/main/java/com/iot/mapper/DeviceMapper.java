package com.iot.mapper;

import com.iot.model.Device;
import com.iot.model.dto.DeviceRegistrationDto;

/**
 * Manual mapper for Device <-> DTO. Kept simple to avoid adding MapStruct as a dependency.
 */
public class DeviceMapper {
    public static Device toEntity(DeviceRegistrationDto dto) {
        if (dto == null) return null;
            return Device.builder()
                .deviceId(dto.getDeviceId())
                .deviceType(dto.getDeviceType())
                .location(dto.getLocation())
                .firmware(dto.getFirmware())
                .status(Device.DeviceStatus.ACTIVE)
                .build();
    }
}

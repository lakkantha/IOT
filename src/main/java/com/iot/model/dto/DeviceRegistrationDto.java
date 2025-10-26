package com.iot.model.dto;

import com.iot.model.common.Location;
import lombok.Data;

@Data
public class DeviceRegistrationDto {
    private String deviceId;
    private String deviceType;
    private Location location;
    private String firmware;
}
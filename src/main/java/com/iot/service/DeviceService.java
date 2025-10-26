package com.iot.service;

import com.iot.model.Device;
import com.iot.model.dto.DeviceRegistrationDto;

public interface DeviceService {
    Device registerDevice(DeviceRegistrationDto registrationDto);
    Device getDeviceById(String deviceId);
    void updateDeviceStatus(String deviceId, Device.DeviceStatus status);
    void deleteDevice(String deviceId);
}
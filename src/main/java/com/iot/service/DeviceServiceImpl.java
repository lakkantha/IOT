package com.iot.service;

import com.iot.model.Device;
import com.iot.model.dto.DeviceRegistrationDto;
import com.iot.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Override
    @Transactional
    public Device registerDevice(DeviceRegistrationDto registrationDto) {
        Device device = Device.builder()
            .deviceId(registrationDto.getDeviceId())
            .deviceType(registrationDto.getDeviceType())
            .location(registrationDto.getLocation())
            .firmware(registrationDto.getFirmware())
            .status(Device.DeviceStatus.ACTIVE)
            .build();
            
        Device saved = deviceRepository.save(device);
        log.info("Registered new device [{}]", saved.getDeviceId());
        return saved;
    }

    @Override
    public Device getDeviceById(String deviceId) {
        return deviceRepository.findByDeviceId(deviceId).orElse(null);
    }

    @Override
    @Transactional
    public void updateDeviceStatus(String deviceId, Device.DeviceStatus status) {
        Device device = getDeviceById(deviceId);
        if (device != null) {
            device.setStatus(status);
            deviceRepository.save(device);
            log.info("Updated status for device [{}] to [{}]", deviceId, status);
        }
    }

    @Override
    @Transactional
    public void deleteDevice(String deviceId) {
        Device device = getDeviceById(deviceId);
        if (device != null) {
            deviceRepository.delete(device);
            log.info("Deleted device [{}]", deviceId);
        }
    }
}
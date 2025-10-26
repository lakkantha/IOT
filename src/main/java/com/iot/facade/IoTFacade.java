package com.iot.facade;

import com.iot.model.Device;
import com.iot.model.SensorData;
import com.iot.model.dto.DeviceRegistrationDto;
import com.iot.model.dto.SensorDataDto;
import com.iot.service.DeviceService;
import com.iot.service.AsyncNotificationService;
import com.iot.service.SensorDataProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

 

/**
 * Facade pattern implementation that simplifies the interaction with the IoT system.
 * This facade encapsulates the complexity of working with devices, sensor data, and messaging.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class IoTFacade {
    private final DeviceService deviceService;
    private final SensorDataProcessingService sensorDataProcessingService;
    private final AsyncNotificationService asyncNotificationService;

    /**
     * Registers a new device and initializes its configuration.
     */
    @Transactional
    public Device registerNewDevice(DeviceRegistrationDto registrationDto) {
        log.info("Registering new device with ID: {}", registrationDto.getDeviceId());
        Device device = deviceService.registerDevice(registrationDto);
        
        // Async notification of device registration (using @Async executor)
        asyncNotificationService.publishDeviceRegistration(registrationDto);
            
        return device;
    }

    /**
     * Processes incoming sensor data with validation and quality assessment.
     */
    @Transactional
    public void processSensorData(SensorDataDto sensorDataDto) {
        log.info("Processing sensor data for device: {}", sensorDataDto.getDeviceId());
        
        // Validate the data first
        sensorDataProcessingService.validateSensorData(sensorDataDto);
        
        // Get the device
        Device device = deviceService.getDeviceById(sensorDataDto.getDeviceId());
        if (device == null) {
            throw new IllegalArgumentException("Device not found: " + sensorDataDto.getDeviceId());
        }

        // Process the sensor data
        SensorData sensorData = sensorDataProcessingService.processSensorData(sensorDataDto, device);
        log.debug("Processed sensor data: {}", sensorData);
        
        // Async send to Kafka for further processing (using @Async executor)
        asyncNotificationService.publishSensorData(sensorDataDto);
    }

    /**
     * Updates device status and handles related operations.
     */
    @Transactional
    public void updateDeviceStatus(String deviceId, Device.DeviceStatus newStatus) {
        log.info("Updating status for device {} to {}", deviceId, newStatus);
        deviceService.updateDeviceStatus(deviceId, newStatus);
    }

    /**
     * Deactivates a device and cleans up related resources.
     */
    @Transactional
    public void deactivateDevice(String deviceId) {
        log.info("Deactivating device: {}", deviceId);
        Device device = deviceService.getDeviceById(deviceId);
        if (device != null) {
            device.setStatus(Device.DeviceStatus.INACTIVE);
            deviceService.updateDeviceStatus(deviceId, Device.DeviceStatus.INACTIVE);
        }
    }

    /**
     * Retrieves device information including its current status and configuration.
     */
    public Device getDeviceInfo(String deviceId) {
        return deviceService.getDeviceById(deviceId);
    }

    /**
     * Completely removes a device and its associated data.
     */
    @Transactional
    public void removeDevice(String deviceId) {
        log.info("Removing device and associated data: {}", deviceId);
        deviceService.deleteDevice(deviceId);
    }
}
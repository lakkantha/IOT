package com.iot.facade;

import com.iot.model.Device;
import com.iot.model.dto.DeviceRegistrationDto;
import com.iot.model.dto.SensorDataDto;
import com.iot.service.AsyncNotificationService;
import com.iot.service.DeviceService;
import com.iot.service.SensorDataProcessingService;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class IoTFacadeTest {

    @Test
    void registerNewDevice_publishesAsync() {
        DeviceService deviceService = mock(DeviceService.class);
        SensorDataProcessingService sdp = mock(SensorDataProcessingService.class);
        AsyncNotificationService asyncSvc = mock(AsyncNotificationService.class);

        IoTFacade facade = new IoTFacade(deviceService, sdp, asyncSvc);

        DeviceRegistrationDto dto = new DeviceRegistrationDto();
        dto.setDeviceId("d-1");
        dto.setDeviceType("temperature");
    Device created = Device.builder().deviceId("d-1").deviceType("temperature").build();
    when(deviceService.registerDevice(any(DeviceRegistrationDto.class))).thenReturn(created);

        facade.registerNewDevice(dto);

        verify(deviceService, times(1)).registerDevice(dto);
        verify(asyncSvc, times(1)).publishDeviceRegistration(dto);
    }

    @Test
    void processSensorData_validatesAndPublishesAsync() {
        DeviceService deviceService = mock(DeviceService.class);
        SensorDataProcessingService sdp = mock(SensorDataProcessingService.class);
        AsyncNotificationService asyncSvc = mock(AsyncNotificationService.class);

        IoTFacade facade = new IoTFacade(deviceService, sdp, asyncSvc);

        SensorDataDto dto = new SensorDataDto();
        dto.setDeviceId("d-1");
        dto.setTimestamp(Instant.now());
        dto.setMeasurements(Map.of("temperature", "25.0"));

    Device device = Device.builder().deviceId("d-1").deviceType("temperature").build();
        when(deviceService.getDeviceById("d-1")).thenReturn(device);

        facade.processSensorData(dto);

        verify(sdp, times(1)).validateSensorData(dto);
        verify(sdp, times(1)).processSensorData(dto, device);
        verify(asyncSvc, times(1)).publishSensorData(dto);
    }
}

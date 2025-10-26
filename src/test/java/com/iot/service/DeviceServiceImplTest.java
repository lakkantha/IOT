package com.iot.service;

import com.iot.model.Device;
import com.iot.model.common.Location;
import com.iot.model.dto.DeviceRegistrationDto;
import com.iot.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeviceServiceImplTest {

    @Test
    void registerDevice_buildsAndSavesActiveDevice() {
        DeviceRepository repo = Mockito.mock(DeviceRepository.class);
        DeviceServiceImpl service = new DeviceServiceImpl(repo);

        DeviceRegistrationDto dto = new DeviceRegistrationDto();
        dto.setDeviceId("d-1");
        dto.setDeviceType("temperature");
        Location loc = new Location();
        loc.setLatitude(1.23);
        loc.setLongitude(4.56);
        dto.setLocation(loc);
        dto.setFirmware("1.0.0");

        when(repo.save(any(Device.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Device saved = service.registerDevice(dto);

        ArgumentCaptor<Device> captor = ArgumentCaptor.forClass(Device.class);
        verify(repo).save(captor.capture());
        Device toSave = captor.getValue();

        assertThat(saved).isNotNull();
        assertThat(toSave.getDeviceId()).isEqualTo("d-1");
        assertThat(toSave.getDeviceType()).isEqualTo("temperature");
        assertThat(toSave.getLocation()).isNotNull();
        assertThat(toSave.getFirmware()).isEqualTo("1.0.0");
        assertThat(toSave.getStatus()).isEqualTo(Device.DeviceStatus.ACTIVE);
    }
}

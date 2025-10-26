package com.iot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.facade.IoTFacade;
import com.iot.model.Device;
import com.iot.model.dto.DeviceRegistrationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IoTFacade iotFacade;

    @Test
    void registerDevice_returns200_onSuccess() throws Exception {
        DeviceRegistrationDto dto = new DeviceRegistrationDto();
        dto.setDeviceId("d-1");
        dto.setDeviceType("temperature");

        Device device = Device.builder().deviceId("d-1").deviceType("temperature").build();
        when(iotFacade.registerNewDevice(any(DeviceRegistrationDto.class))).thenReturn(device);

        mockMvc.perform(post("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void registerDevice_returns409_onDuplicate() throws Exception {
        DeviceRegistrationDto dto = new DeviceRegistrationDto();
        dto.setDeviceId("d-1");
        dto.setDeviceType("temperature");

        when(iotFacade.registerNewDevice(any(DeviceRegistrationDto.class)))
                .thenThrow(new DataIntegrityViolationException("duplicate"));

        mockMvc.perform(post("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }
}

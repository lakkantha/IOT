package com.iot.controller;

import com.iot.facade.IoTFacade;
import com.iot.model.Device;
import com.iot.model.dto.DeviceRegistrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final IoTFacade iotFacade;

    @PostMapping
    public ResponseEntity<Device> registerDevice(@RequestBody DeviceRegistrationDto registrationDto) {
        try {
            Device device = iotFacade.registerNewDevice(registrationDto);
            return ResponseEntity.ok(device);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<Device> getDevice(@PathVariable String deviceId) {
        Device device = iotFacade.getDeviceInfo(deviceId);
        if (device == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(device);
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<Void> deleteDevice(@PathVariable String deviceId) {
        iotFacade.removeDevice(deviceId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{deviceId}/status")
    public ResponseEntity<Void> updateDeviceStatus(
            @PathVariable String deviceId,
            @RequestParam Device.DeviceStatus status) {
        iotFacade.updateDeviceStatus(deviceId, status);
        return ResponseEntity.ok().build();
    }
}
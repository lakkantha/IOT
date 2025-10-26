package com.iot.controller;

import com.iot.facade.IoTFacade;
import com.iot.model.dto.SensorDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class SensorDataController {

    private final IoTFacade iotFacade;

    @PostMapping
    public ResponseEntity<Void> ingestSensorData(@RequestBody SensorDataDto sensorDataDto) {
        iotFacade.processSensorData(sensorDataDto);
        return ResponseEntity.accepted().build();
    }
}
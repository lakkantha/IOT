package com.iot.service;

import com.iot.model.dto.DeviceRegistrationDto;
import com.iot.model.dto.SensorDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncNotificationService {

    private final KafkaProducerService kafkaProducerService;

    @Async("ioTaskExecutor")
    public void publishDeviceRegistration(DeviceRegistrationDto registrationDto) {
        kafkaProducerService.sendDeviceRegistration(registrationDto)
                .whenComplete((res, ex) -> {
                    if (ex != null) {
                        log.error("Async publish device registration failed for {}", registrationDto.getDeviceId(), ex);
                    }
                });
    }

    @Async("ioTaskExecutor")
    public void publishSensorData(SensorDataDto sensorDataDto) {
        kafkaProducerService.sendSensorData(sensorDataDto)
                .whenComplete((res, ex) -> {
                    if (ex != null) {
                        log.error("Async publish sensor data failed for {}", sensorDataDto.getDeviceId(), ex);
                    }
                });
    }
}

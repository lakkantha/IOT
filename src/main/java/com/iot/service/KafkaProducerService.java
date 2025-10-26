package com.iot.service;

import com.iot.config.KafkaConfig;
import com.iot.model.dto.DeviceRegistrationDto;
import com.iot.model.dto.SensorDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CompletableFuture<SendResult<String, Object>> sendSensorData(SensorDataDto sensorData) {
        return kafkaTemplate.send(KafkaConfig.SENSOR_DATA_TOPIC, sensorData.getDeviceId(), sensorData)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Sent sensor data from device [{}] with timestamp [{}]",
                                sensorData.getDeviceId(), sensorData.getTimestamp());
                    } else {
                        log.error("Unable to send sensor data for device [{}]", 
                                sensorData.getDeviceId(), ex);
                    }
                });
    }

    public CompletableFuture<SendResult<String, Object>> sendDeviceRegistration(DeviceRegistrationDto device) {
        return kafkaTemplate.send(KafkaConfig.DEVICE_REGISTRATION_TOPIC, device.getDeviceId(), device)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Sent device registration for device [{}]", device.getDeviceId());
                    } else {
                        log.error("Unable to send device registration for device [{}]", 
                                device.getDeviceId(), ex);
                    }
                });
    }
}
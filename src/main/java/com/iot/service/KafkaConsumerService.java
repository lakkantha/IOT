package com.iot.service;

import com.iot.config.KafkaConfig;
import com.iot.model.Device;
import com.iot.model.dto.DeviceRegistrationDto;
import com.iot.model.dto.SensorDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final DeviceService deviceService;
    private final SensorDataProcessingService dataProcessingService;

    @KafkaListener(
        topics = KafkaConfig.SENSOR_DATA_TOPIC,
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional
    public void consumeSensorData(@Payload List<SensorDataDto> sensorDataList) {
        log.info("Received batch of {} sensor data records", sensorDataList.size());
        
        sensorDataList.forEach(sensorDataDto -> {
            try {
                Device device = deviceService.getDeviceById(sensorDataDto.getDeviceId());
                if (device != null && device.isOperational()) {
                    dataProcessingService.processSensorData(sensorDataDto, device);
                    log.debug("Processed sensor data for device [{}]", device.getDeviceId());
                } else {
                    log.warn("Received data for non-existent or non-operational device [{}]", 
                            sensorDataDto.getDeviceId());
                }
            } catch (Exception e) {
                log.error("Error processing sensor data for device [{}]", 
                        sensorDataDto.getDeviceId(), e);
            }
        });
    }

    @KafkaListener(
        topics = KafkaConfig.DEVICE_REGISTRATION_TOPIC,
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional
    public void consumeDeviceRegistration(@Payload List<DeviceRegistrationDto> deviceRegistrations) {
        log.info("Received batch of {} device registrations", deviceRegistrations.size());
        
        deviceRegistrations.forEach(registration -> {
            try {
                Device device = deviceService.registerDevice(registration);
                log.info("Successfully registered device [{}]", device.getDeviceId());
            } catch (Exception e) {
                log.error("Error registering device [{}]", registration.getDeviceId(), e);
            }
        });
    }
}
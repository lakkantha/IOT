package com.iot.service;

import com.iot.config.ApplicationConfig;
import com.iot.model.Device;
import com.iot.model.SensorData;
import com.iot.model.common.Location;
import com.iot.model.dto.SensorDataDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ApplicationConfig.class, SensorDataProcessingServiceImpl.class})
@ComponentScan(basePackageClasses = {ApplicationConfig.class, SensorDataProcessingServiceImpl.class})
@TestPropertySource(properties = {
        "spring.application.name=iot-data-processing-test",
        "app.device.batch.size=2"
})
class SensorDataProcessingServiceImplTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @org.springframework.beans.factory.annotation.Autowired
    private SensorDataProcessingServiceImpl service;

    @Test
    void processSensorData_setsQualityAndFields() {
        Device device = Device.builder().deviceId("d-1").deviceType("temperature").location(new Location()).build();
        SensorDataDto dto = new SensorDataDto();
        dto.setDeviceId("d-1");
        dto.setTimestamp(Instant.now());
        dto.setMeasurements(Map.of("temperature", "25.0"));

        SensorData sd = service.processSensorData(dto, device);
        assertThat(sd.getDevice()).isEqualTo(device);
        assertThat(sd.getMeasurements()).containsEntry("temperature", "25.0");
        assertThat(sd.getDataQuality()).isEqualTo(SensorData.DataQuality.GOOD);
    }
}

package com.iot.model;

import com.iot.model.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "sensor_data")
@Getter
@Setter
public class SensorData extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
    
    @Column(nullable = false)
    private Instant timestamp;
    
    @ElementCollection
    @CollectionTable(name = "sensor_measurements",
            joinColumns = @JoinColumn(name = "sensor_data_id"))
    @MapKeyColumn(name = "measurement_key")
    @Column(name = "measurement_value")
    private Map<String, String> measurements = new HashMap<>();
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DataQuality dataQuality;
    
    public enum DataQuality {
        GOOD, QUESTIONABLE, BAD
    }
    
    public void addMeasurement(String key, String value) {
        measurements.put(key, value);
    }
    
    public String getMeasurement(String key) {
        return measurements.get(key);
    }
}
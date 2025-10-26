package com.iot.model;

import com.iot.model.common.BaseEntity;
import com.iot.model.common.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "devices")
@Getter
@Setter
public class Device extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String deviceId;
    
    @Column(nullable = false)
    private String deviceType;
    
    @Embedded
    private Location location;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeviceStatus status = DeviceStatus.INACTIVE;
    
    @Column
    private String firmware;
    
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SensorData> sensorData = new HashSet<>();

    // Private constructor for builder
    private Device() {}
    
    public static DeviceBuilder builder() {
        return new DeviceBuilder();
    }
    
    // Builder class
    public static class DeviceBuilder {
        private Device device;
        
        DeviceBuilder() {
            device = new Device();
        }
        
        public DeviceBuilder deviceId(String deviceId) {
            device.setDeviceId(deviceId);
            return this;
        }
        
        public DeviceBuilder deviceType(String deviceType) {
            device.setDeviceType(deviceType);
            return this;
        }
        
        public DeviceBuilder location(Location location) {
            device.setLocation(location);
            return this;
        }
        
        public DeviceBuilder status(DeviceStatus status) {
            device.setStatus(status);
            return this;
        }
        
        public DeviceBuilder firmware(String firmware) {
            device.setFirmware(firmware);
            return this;
        }
        
        public Device build() {
            validateDeviceData();
            return device;
        }
        
        private void validateDeviceData() {
            if (device.getDeviceId() == null || device.getDeviceId().trim().isEmpty()) {
                throw new IllegalStateException("Device ID cannot be null or empty");
            }
            if (device.getDeviceType() == null || device.getDeviceType().trim().isEmpty()) {
                throw new IllegalStateException("Device type cannot be null or empty");
            }
        }
    }
    
    public enum DeviceStatus {
        ACTIVE, INACTIVE, MAINTENANCE, ERROR
    }
    
    public boolean isOperational() {
        return status == DeviceStatus.ACTIVE;
    }
    
    public void addSensorData(SensorData data) {
        sensorData.add(data);
        data.setDevice(this);
    }
}
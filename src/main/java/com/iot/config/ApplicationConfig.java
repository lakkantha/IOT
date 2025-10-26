package com.iot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
public class ApplicationConfig {
    private static ApplicationConfig instance;
    private final ConcurrentHashMap<String, Object> configCache = new ConcurrentHashMap<>();
    
    @Value("${spring.application.name:iot-data-processing}")
    private String applicationName;
    
    @Value("${app.data.retention.days:30}")
    private int dataRetentionDays;
    
    @Value("${app.device.batch.size:100}")
    private int deviceBatchSize;
    
    private ApplicationConfig() {
        // Private constructor to prevent instantiation
    }
    
    @PostConstruct
    private void init() {
        instance = this;
        // Initialize cache with common configurations
        configCache.put("applicationName", applicationName);
        configCache.put("dataRetentionDays", dataRetentionDays);
        configCache.put("deviceBatchSize", deviceBatchSize);
    }
    
    public static ApplicationConfig getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ApplicationConfig not initialized yet. Ensure Spring context is active.");
        }
        return instance;
    }
    
    public Object getConfigValue(String key) {
        return configCache.get(key);
    }
    
    public void setConfigValue(String key, Object value) {
        configCache.put(key, value);
    }
    
    public void clearCache() {
        configCache.clear();
        // Reinitialize with default values
        init();
    }
}
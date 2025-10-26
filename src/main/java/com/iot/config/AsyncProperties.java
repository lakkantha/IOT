package com.iot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.async")
public class AsyncProperties {
    /**
     * Core pool size for ioTaskExecutor.
     */
    private int corePoolSize = 8;
    /**
     * Max pool size for ioTaskExecutor.
     */
    private int maxPoolSize = 32;
    /**
     * Queue capacity for ioTaskExecutor.
     */
    private int queueCapacity = 1000;
    /**
     * Thread name prefix for ioTaskExecutor threads.
     */
    private String threadNamePrefix = "io-exec-";
    /**
     * Seconds to wait for tasks to complete on shutdown.
     */
    private int awaitTerminationSeconds = 30;
    /**
     * Whether to wait for tasks to complete on shutdown.
     */
    private boolean waitForTasksToCompleteOnShutdown = true;
}

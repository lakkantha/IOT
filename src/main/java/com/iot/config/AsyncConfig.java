package com.iot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    private final AsyncProperties props;

    public AsyncConfig(AsyncProperties props) {
        this.props = props;
    }

    @Bean(name = "ioTaskExecutor")
    public Executor ioTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(props.getCorePoolSize());
        executor.setMaxPoolSize(props.getMaxPoolSize());
        executor.setQueueCapacity(props.getQueueCapacity());
        executor.setThreadNamePrefix(props.getThreadNamePrefix());
        executor.setAwaitTerminationSeconds(props.getAwaitTerminationSeconds());
        executor.setWaitForTasksToCompleteOnShutdown(props.isWaitForTasksToCompleteOnShutdown());
        executor.initialize();
        return executor;
    }
}

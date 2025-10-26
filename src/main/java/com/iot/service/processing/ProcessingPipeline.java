package com.iot.service.processing;

import com.iot.model.SensorData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessingPipeline {
    private final List<DataProcessor> stages;

    public ProcessingPipeline(List<DataProcessor> stages) {
        this.stages = stages;
    }

    public void execute(SensorData data) {
        for (DataProcessor stage : stages) {
            ProcessingResult result = stage.process(data);
            if (!result.success()) {
                // stop pipeline on failure for now; in future we can add retries or dead-letter handling
                break;
            }
        }
    }
}

package com.iot.service.processing;

import com.iot.model.SensorData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProcessingPipelineTest {

    @Test
    void pipeline_executesStagesAndStopsOnFailure() {
        List<String> calls = new ArrayList<>();

        DataProcessor good = data -> {
            calls.add("good");
            return ProcessingResult.ok();
        };

        DataProcessor bad = data -> {
            calls.add("bad");
            return ProcessingResult.fail("fail");
        };

        DataProcessor after = data -> {
            calls.add("after");
            return ProcessingResult.ok();
        };

        ProcessingPipeline pipeline = new ProcessingPipeline(List.of(good, bad, after));
        SensorData data = new SensorData();
        pipeline.execute(data);

        assertEquals(2, calls.size());
        assertEquals("good", calls.get(0));
        assertEquals("bad", calls.get(1));
    }
}

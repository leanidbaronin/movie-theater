package com.jpmc.theater.export;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.theater.domain.Schedule;

public class ScheduleJsonFormatter implements ScheduleFormatter {

    private final ObjectMapper objectMapper;

    private final ScheduleSnapshotBuilder snapshotBuilder;

    public ScheduleJsonFormatter(ObjectMapper objectMapper, ScheduleSnapshotBuilder snapshotBuilder) {
        this.objectMapper = objectMapper;
        this.snapshotBuilder = snapshotBuilder;
    }

    @Override
    public String format(Schedule schedule) {
        ScheduleSnapshot scheduleSnapshot = snapshotBuilder.build(schedule);
        try {
            return objectMapper.writeValueAsString(scheduleSnapshot);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
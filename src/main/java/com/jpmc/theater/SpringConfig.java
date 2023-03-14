package com.jpmc.theater;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.theater.domain.Schedule;
import com.jpmc.theater.export.ScheduleConsoleFormatter;
import com.jpmc.theater.export.ScheduleJsonFormatter;
import com.jpmc.theater.export.ScheduleSnapshotBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

public class SpringConfig {

    @Bean
    public TheaterReservationService provideTheaterReservationService(Schedule schedule) {
        return new TheaterReservationService(schedule);
    }

    @Bean
    public ScheduleJsonFormatter provideScheduleJsonFormatter(ObjectMapper objectMapper,
                                                              ScheduleSnapshotBuilder snapshotBuilder) {
        return new ScheduleJsonFormatter(objectMapper, snapshotBuilder);
    }

    @Bean
    public ScheduleConsoleFormatter provideScheduleConsoleFormatter(ScheduleSnapshotBuilder snapshotBuilder) {
        return new ScheduleConsoleFormatter(snapshotBuilder);
    }

    @Bean
    public ScheduleSnapshotBuilder provideScheduleSnapshotBuilder(LocalDateProvider localDateProvider) {
        return new ScheduleSnapshotBuilder(localDateProvider);
    }

    @Bean
    public Schedule provideSchedule(LocalDateProvider localDateProvider) {
        return new ScheduleHardcodedProvider(localDateProvider).provide();
    }

    @Bean
    public LocalDateProvider provideLocalDateProvider() {
        return new LocalDateProvider(LocalDate::now);
    }

    @Bean
    public ObjectMapper provideJsonObjectMapper() {
        return new ObjectMapper();
    }
}
package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleHardcodedProviderTests {

    @Test
    public void provide_returnsSchedule() {
        var scheduleProvider = new ScheduleHardcodedProvider(new LocalDateProvider(LocalDate::now));

        var schedule = scheduleProvider.provide();

        assertNotNull(schedule);
        assertFalse(schedule.showings().isEmpty());
    }
}
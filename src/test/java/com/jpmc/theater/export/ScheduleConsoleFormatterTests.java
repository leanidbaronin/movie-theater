package com.jpmc.theater.export;

import com.jpmc.theater.LocalDateProvider;
import com.jpmc.theater.domain.Movie;
import com.jpmc.theater.domain.Schedule;
import com.jpmc.theater.domain.Showing;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScheduleConsoleFormatterTests {

    private static final LocalDate DEFAULT_DATE = LocalDate.of(2023,1, 1);

    @Test
    public void print_printsValidConsoleText() {
        var consolePrinter = new ScheduleConsoleFormatter(
                new ScheduleSnapshotBuilder(new LocalDateProvider(() -> DEFAULT_DATE)));
        var movie = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(120), 100, /* isSpecial= */ false);
        var showings = List.of(
                new Showing(movie, 1, LocalDateTime.of(DEFAULT_DATE, LocalTime.of(10, 0))),
                new Showing(movie, 2, LocalDateTime.of(DEFAULT_DATE, LocalTime.of(10, 0))));
        var schedule = new Schedule(showings);

        var actualConsoleText = consolePrinter.format(schedule);

        assertEquals("""
                2023-01-01
                ===================================================
                1: 2023-01-01T10:00 Spider-Man: No Way Home (2 hours 0 minutes) $97.0
                2: 2023-01-01T10:00 Spider-Man: No Way Home (2 hours 0 minutes) $98.0
                ===================================================
                """, actualConsoleText);
    }
}
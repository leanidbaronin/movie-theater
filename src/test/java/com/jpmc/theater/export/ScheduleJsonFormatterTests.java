package com.jpmc.theater.export;

import com.fasterxml.jackson.databind.ObjectMapper;
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

public class ScheduleJsonFormatterTests {

    private static final LocalDate DEFAULT_DATE = LocalDate.of(2023,1, 1);

    @Test
    public void print_printsValidJson() {
        var jsonPrinter = new ScheduleJsonFormatter(
                new ObjectMapper(),
                new ScheduleSnapshotBuilder(new LocalDateProvider(() -> DEFAULT_DATE)));
        var movie = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(120), 100, /* isSpecial= */ false);
        var showings = List.of(
                new Showing(movie, 1, LocalDateTime.of(DEFAULT_DATE, LocalTime.of(10, 0))),
                new Showing(movie, 2, LocalDateTime.of(DEFAULT_DATE, LocalTime.of(10, 0))));
        var schedule = new Schedule(showings);

        var actualJson = jsonPrinter.format(schedule);

        assertEquals("{" +
                "\"currentDateTime\":\"2023-01-01\"," +
                "\"showingLines\":[" +
                "{\"sequenceOfTheDay\":\"1\"," +
                "\"showStartDateTime\":\"2023-01-01T10:00\"," +
                "\"movieTitle\":\"Spider-Man: No Way Home\"," +
                "\"runningTime\":\"2 hours 0 minutes\"," +
                "\"ticketPrice\":\"97.0\"}," +
                "{\"sequenceOfTheDay\":\"2\"," +
                "\"showStartDateTime\":\"2023-01-01T10:00\"," +
                "\"movieTitle\":\"Spider-Man: No Way Home\"," +
                "\"runningTime\":\"2 hours 0 minutes\"," +
                "\"ticketPrice\":\"98.0\"}]}",
                actualJson);
    }
}
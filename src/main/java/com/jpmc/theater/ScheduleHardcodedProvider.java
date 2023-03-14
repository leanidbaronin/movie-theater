package com.jpmc.theater;

import com.jpmc.theater.domain.Movie;
import com.jpmc.theater.domain.Schedule;
import com.jpmc.theater.domain.Showing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ScheduleHardcodedProvider implements ScheduleProvider {

    private final LocalDateProvider localDateProvider;

    public ScheduleHardcodedProvider(LocalDateProvider localDateProvider) {
        this.localDateProvider = localDateProvider;
    }

    public Schedule provide() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.50, true);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, false);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, false);
        return new Schedule(List.of(
                new Showing(turningRed, 1, LocalDateTime.of(localDateProvider.getCurrentDate(), LocalTime.of(9, 0))),
                new Showing(spiderMan, 2, LocalDateTime.of(localDateProvider.getCurrentDate(), LocalTime.of(11, 0))),
                new Showing(theBatMan, 3, LocalDateTime.of(localDateProvider.getCurrentDate(), LocalTime.of(12, 50))),
                new Showing(turningRed, 4, LocalDateTime.of(localDateProvider.getCurrentDate(), LocalTime.of(14, 30))),
                new Showing(spiderMan, 5, LocalDateTime.of(localDateProvider.getCurrentDate(), LocalTime.of(16, 10))),
                new Showing(theBatMan, 6, LocalDateTime.of(localDateProvider.getCurrentDate(), LocalTime.of(17, 50))),
                new Showing(turningRed, 7, LocalDateTime.of(localDateProvider.getCurrentDate(), LocalTime.of(19, 30))),
                new Showing(spiderMan, 8, LocalDateTime.of(localDateProvider.getCurrentDate(), LocalTime.of(21, 10))),
                new Showing(theBatMan, 9, LocalDateTime.of(localDateProvider.getCurrentDate(), LocalTime.of(23, 0)))
        ));
    }
}
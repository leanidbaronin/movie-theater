package com.jpmc.theater;

import com.jpmc.theater.domain.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TheaterReservationServiceTests {

    private static final LocalDate DEFAULT_DATE = LocalDate.of(2023,1, 1);
    private static final Customer DEFAULT_CUSTOMER = new Customer("John Doe");
    private static final Movie DEFAULT_MOVIE = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.50, true);

    @Test
    public void reserve_reservesIfSequenceIsCorrect() {
        var showing = new Showing(DEFAULT_MOVIE, 1, LocalDateTime.of(DEFAULT_DATE, LocalTime.of(9, 0)));
        var schedule = new Schedule(List.of(showing));
        var reservationService = new TheaterReservationService(schedule);

        Reservation actualReservation = reservationService.reserve(DEFAULT_CUSTOMER, 1, 10);

        assertEquals(new Reservation(DEFAULT_CUSTOMER, showing, 10, 95), actualReservation);
    }

    @Test
    public void reserve_reservesIfSequenceIsNotCorrect() {
        var showing = new Showing(DEFAULT_MOVIE, 1, LocalDateTime.of(DEFAULT_DATE, LocalTime.of(9, 0)));
        var schedule = new Schedule(List.of(showing));
        var reservationService = new TheaterReservationService(schedule);

        assertThrows(IllegalArgumentException.class, () -> reservationService.reserve(DEFAULT_CUSTOMER, 2, 10));
    }
}

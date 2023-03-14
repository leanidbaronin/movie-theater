package com.jpmc.theater;

import com.jpmc.theater.domain.Movie;
import com.jpmc.theater.domain.Showing;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketPriceCalculatorTests {

    private static final int DEFAULT_SEQUENCE = 99;

    private static final LocalDate DEFAULT_DATE = LocalDate.of(2023,1, 1);
    private static final LocalTime DEFAULT_TIME = LocalTime.of(0, 0);
    private static final LocalDateTime DEFAULT_LOCAL_DATE_TIME = LocalDateTime.of(DEFAULT_DATE, DEFAULT_TIME);

    @Test
    void calculateTicketPrice_calculatesNoSpecialDiscount() {
        var movie = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(120), 100, /* isSpecial= */ false);
        var showing = new Showing(movie, DEFAULT_SEQUENCE, DEFAULT_LOCAL_DATE_TIME);

        double actualTicketPrice = TicketPriceCalculator.calculateTicketPrice(showing);

        assertEquals(100.0, actualTicketPrice);
    }

    @Test
    void calculateTicketPrice_calculatesSpecialDiscount() {
        var movie = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(120), 100, /* isSpecial= */ true);
        var showing = new Showing(movie, DEFAULT_SEQUENCE, DEFAULT_LOCAL_DATE_TIME);

        double actualTicketPrice = TicketPriceCalculator.calculateTicketPrice(showing);

        assertEquals(80.0, actualTicketPrice);
    }

    @Test
    void calculateTicketPrice_calculatesNoSequenceDiscount() {
        var movie = buildMovieWithPrice(100);
        var showing = new Showing(movie, DEFAULT_SEQUENCE, DEFAULT_LOCAL_DATE_TIME);

        double actualTicketPrice = TicketPriceCalculator.calculateTicketPrice(showing);

        assertEquals(100, actualTicketPrice);
    }

    @Test
    void calculateTicketPrice_calculatesFirstSequenceDiscount() {
        var movie = buildMovieWithPrice(100);
        var showing = new Showing(movie, 1, DEFAULT_LOCAL_DATE_TIME);

        double actualTicketPrice = TicketPriceCalculator.calculateTicketPrice(showing);

        assertEquals(97.0, actualTicketPrice);
    }

    @Test
    void calculateTicketPrice_calculatesSecondSequenceDiscount() {
        var movie = buildMovieWithPrice(100);
        var showing = new Showing(movie, 2, DEFAULT_LOCAL_DATE_TIME);

        double actualTicketPrice = TicketPriceCalculator.calculateTicketPrice(showing);

        assertEquals(98.0, actualTicketPrice);
    }

    @Test
    void calculateTicketPrice_calculatesNoTimeDiscountFor1059AM() {
        var movie = buildMovieWithPrice(100);
        var dateTime = buildDateTime(LocalTime.of(10, 59));
        var showing = new Showing(movie, DEFAULT_SEQUENCE, dateTime);

        double actualTicketPrice = TicketPriceCalculator.calculateTicketPrice(showing);

        assertEquals(100.0, actualTicketPrice);
    }

    @Test
    void calculateTicketPrice_calculatesTimeDiscountFor11AM() {
        var movie = buildMovieWithPrice(100);
        var dateTime = buildDateTime(LocalTime.of(11, 0));
        var showing = new Showing(movie, DEFAULT_SEQUENCE, dateTime);

        double actualTicketPrice = TicketPriceCalculator.calculateTicketPrice(showing);

        assertEquals(75.0, actualTicketPrice);
    }

    @Test
    void calculateTicketPrice_calculatesTimeDiscountFor1330AM() {
        var movie = buildMovieWithPrice(100);
        var dateTime = buildDateTime(LocalTime.of(13, 30));
        var showing = new Showing(movie, DEFAULT_SEQUENCE, dateTime);

        double actualTicketPrice = TicketPriceCalculator.calculateTicketPrice(showing);

        assertEquals(75.0, actualTicketPrice);
    }

    @Test
    void calculateTicketPrice_calculatesTimeDiscountFor4PM() {
        var movie = buildMovieWithPrice(100);
        var dateTime = buildDateTime(LocalTime.of(16, 0));
        var showing = new Showing(movie, DEFAULT_SEQUENCE, dateTime);

        double actualTicketPrice = TicketPriceCalculator.calculateTicketPrice(showing);

        assertEquals(75.0, actualTicketPrice);
    }

    @Test
    void calculateTicketPrice_calculatesNoTimeDiscountFor401PM() {
        var movie = buildMovieWithPrice(100);
        var dateTime = buildDateTime(LocalTime.of(16, 1));
        var showing = new Showing(movie, DEFAULT_SEQUENCE, dateTime);

        double actualTicketPrice = TicketPriceCalculator.calculateTicketPrice(showing);

        assertEquals(100.0, actualTicketPrice);
    }

    @Test
    void calculateTicketPrice_calculatesNoDateDiscount() {
        var movie = buildMovieWithPrice(100);
        var dateTime = buildDateTime(LocalDate.of(2023,1, 1));
        var showing = new Showing(movie, DEFAULT_SEQUENCE, dateTime);

        double actualTicketPrice = TicketPriceCalculator.calculateTicketPrice(showing);

        assertEquals(100.0, actualTicketPrice);
    }

    @Test
    void calculateTicketPrice_calculatesDateDiscount() {
        var movie = buildMovieWithPrice(100);
        var dateTime = buildDateTime(LocalDate.of(2023,1, 7));
        var showing = new Showing(movie, DEFAULT_SEQUENCE, dateTime);

        double actualTicketPrice = TicketPriceCalculator.calculateTicketPrice(showing);

        assertEquals(99.0, actualTicketPrice);
    }

    @Test
    void calculateTicketPrice_appliesOnlyBiggestDiscount() {
        var movie = buildMovieWithPrice(100);
        // Date discount -$1
        var dateTime = buildDateTime(LocalDate.of(2023,1, 7));
        // Sequence discount -$2
        var showing = new Showing(movie, 2, dateTime);

        double actualTicketPrice = TicketPriceCalculator.calculateTicketPrice(showing);

        // Sequence discount -$2 win
        assertEquals(98.0, actualTicketPrice);
    }

    @Test
    void calculateTicketPrice_appliesDateDiscountAndAmount() {
        var movie = buildMovieWithPrice(100);
        // Date discount -$1
        var dateTime = buildDateTime(LocalDate.of(2023,1, 7));
        var showing = new Showing(movie, DEFAULT_SEQUENCE, dateTime);

        double actualTicketPrice = TicketPriceCalculator.calculateTicketPrice(showing, 2);

        // Two tickets * $99 each = $198 total
        assertEquals(198, actualTicketPrice);
    }

    private Movie buildMovieWithPrice(double price) {
        return new Movie("Spider-Man: No Way Home", Duration.ofMinutes(120), price, /* isSpecial= */ false);
    }

    private LocalDateTime buildDateTime(LocalTime localTime) {
        return LocalDateTime.of(DEFAULT_DATE, localTime);
    }

    private LocalDateTime buildDateTime(LocalDate localDate) {
        return LocalDateTime.of(localDate, DEFAULT_TIME);
    }
}
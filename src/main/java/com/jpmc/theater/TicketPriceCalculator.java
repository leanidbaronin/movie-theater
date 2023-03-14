package com.jpmc.theater;

import com.jpmc.theater.domain.Movie;
import com.jpmc.theater.domain.Showing;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class TicketPriceCalculator {

    public static double calculateTicketPrice(Showing showing, int ticketsAmount) {
        return calculateTicketPrice(showing) * ticketsAmount;
    }

    public static double calculateTicketPrice(Showing showing) {
        Movie movie = showing.movie();
        return movie.ticketPrice() - getDiscount(showing);
    }

    private static double getDiscount(Showing showing) {
        List<Double> possibleDiscounts = new ArrayList<>();

        Optional<Double> specialDiscount = calculateSpecialDiscount(showing.movie());
        specialDiscount.ifPresent(possibleDiscounts::add);

        Optional<Double> sequenceDiscount = calculateSequenceDiscount(showing.sequenceOfTheDay());
        sequenceDiscount.ifPresent(possibleDiscounts::add);

        Optional<Double> timeDiscount = calculateTimeDiscount(showing.showStartDateTime(), showing.movie().ticketPrice());
        timeDiscount.ifPresent(possibleDiscounts::add);

        Optional<Double> dateDiscount = calculateDateDiscount(showing.showStartDateTime());
        dateDiscount.ifPresent(possibleDiscounts::add);

        return possibleDiscounts.stream().mapToDouble(Double::doubleValue).max().orElse(0);
    }

    private static Optional<Double> calculateSpecialDiscount(Movie movie) {
        // 20% discount for special movie
        if (movie.isSpecial()) {
            return Optional.of(movie.ticketPrice() * 0.2);
        }
        return Optional.empty();
    }

    private static Optional<Double> calculateSequenceDiscount(int showSequence) {
        // $3 discount for 1st show
        if (showSequence == 1) {
            return Optional.of(3.0);
        }
        // $2 discount for 2nd show
        if (showSequence == 2) {
            return Optional.of(2.0);
        }
        return Optional.empty();
    }

    private static Optional<Double> calculateTimeDiscount(LocalDateTime localDateTime, double ticketPrice) {
        // 25% discount for any movies starting between 11AM ~ 4pm
        boolean notInDiscountRange =
                localDateTime.toLocalTime().isBefore(LocalTime.of(11, 0))
                || localDateTime.toLocalTime().isAfter(LocalTime.of(16, 0));
        boolean inDiscountRange = !notInDiscountRange;
        if (inDiscountRange) {
            return Optional.of(ticketPrice * 0.25);
        }
        return Optional.empty();
    }

    private static Optional<Double> calculateDateDiscount(LocalDateTime showStartDateTime) {
        // Any movies showing on 7th, you'll get 1$ discount
        if (showStartDateTime.getDayOfMonth() == 7) {
            return Optional.of(1.0);
        }
        return Optional.empty();
    }
}
package com.jpmc.theater;

import com.jpmc.theater.domain.*;

import java.util.List;

public final class TheaterReservationService {

    private final Schedule schedule;

    public TheaterReservationService(Schedule schedule) {
        this.schedule = schedule;
    }

    public Reservation reserve(Customer customer, int sequence, int ticketsAmount) {
        List<Showing> showings = schedule.showings();
        if (sequence <= 0 || showings.size() < sequence) {
            throw new IllegalArgumentException("Not able to find any showing for given sequence: " + sequence);
        }
        Showing showing = showings.get(sequence - 1);
        return new Reservation(customer, showing, ticketsAmount,
                TicketPriceCalculator.calculateTicketPrice(showing, ticketsAmount));
    }
}
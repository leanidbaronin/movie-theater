package com.jpmc.theater.domain;

public record Reservation (Customer customer, Showing showing, int ticketsAmount, double totalPrice) {}
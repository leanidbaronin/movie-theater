package com.jpmc.theater.domain;

import java.time.Duration;

public record Movie (String title, Duration runningTime, double ticketPrice, boolean isSpecial) {}
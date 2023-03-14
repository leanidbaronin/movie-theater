package com.jpmc.theater.domain;

import java.time.LocalDateTime;

public record Showing (Movie movie, int sequenceOfTheDay, LocalDateTime showStartDateTime) {}
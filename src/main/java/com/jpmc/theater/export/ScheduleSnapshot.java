package com.jpmc.theater.export;

import java.util.List;

record ScheduleSnapshot(String currentDateTime, List<ShowingLine> showingLines) {}

record ShowingLine(String sequenceOfTheDay, String showStartDateTime, String movieTitle, String runningTime,
                   String ticketPrice) {}
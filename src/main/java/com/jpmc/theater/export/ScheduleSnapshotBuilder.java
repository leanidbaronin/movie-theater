package com.jpmc.theater.export;

import com.jpmc.theater.LocalDateProvider;
import com.jpmc.theater.TicketPriceCalculator;
import com.jpmc.theater.domain.Schedule;
import com.jpmc.theater.domain.Showing;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ScheduleSnapshotBuilder {

    private final LocalDateProvider localDateProvider;

    public ScheduleSnapshotBuilder(LocalDateProvider localDateProvider) {
        this.localDateProvider = localDateProvider;
    }

    public ScheduleSnapshot build(Schedule schedule) {
        return new ScheduleSnapshot(localDateProvider.getCurrentDate().toString(), buildScheduleSnapshotLines(schedule));
    }

    private List<ShowingLine> buildScheduleSnapshotLines(Schedule schedule) {
        return schedule.showings().stream().map(this::buildSnapshotLine).collect(Collectors.toList());
    }

    private ShowingLine buildSnapshotLine(Showing showing) {
        return new ShowingLine(
                String.valueOf(showing.sequenceOfTheDay()),
                showing.showStartDateTime().toString(),
                showing.movie().title(),
                humanReadableFormat(showing.movie().runningTime()),
                String.valueOf(TicketPriceCalculator.calculateTicketPrice(showing)));
    }

    public static String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());
        return String.format("%s hour%s %s minute%s", hour, returnSForPlurals(hour), remainingMin,
                returnSForPlurals(remainingMin));
    }

    // Returns (s) postfix for plurals.
    private static String returnSForPlurals(long value) {
        if (value == 1) {
            return "";
        } else {
            return "s";
        }
    }
}
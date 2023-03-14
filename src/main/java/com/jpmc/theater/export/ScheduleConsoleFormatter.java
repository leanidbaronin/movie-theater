package com.jpmc.theater.export;

import com.jpmc.theater.domain.Schedule;

public class ScheduleConsoleFormatter implements ScheduleFormatter {

    private final ScheduleSnapshotBuilder snapshotBuilder;

    public ScheduleConsoleFormatter(ScheduleSnapshotBuilder snapshotBuilder) {
        this.snapshotBuilder = snapshotBuilder;
    }

    public String format(Schedule schedule) {
        ScheduleSnapshot scheduleSnapshot = snapshotBuilder.build(schedule);
        return printSnapshot(scheduleSnapshot);
    }

    private static String printSnapshot(ScheduleSnapshot scheduleSnapshot) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(scheduleSnapshot.currentDateTime()).append("\n");
        stringBuilder.append("===================================================\n");
        scheduleSnapshot.showingLines().forEach(showingLine -> appendShowing(stringBuilder, showingLine));
        stringBuilder.append("===================================================\n");
        return stringBuilder.toString();
    }

    private static void appendShowing(StringBuilder stringBuilder, ShowingLine showingLine) {
        stringBuilder.append(showingLine.sequenceOfTheDay()).append(": ")
                .append(showingLine.showStartDateTime())
                .append(" ").append(showingLine.movieTitle())
                .append(" (").append(showingLine.runningTime()).append(")")
                .append(" $").append(showingLine.ticketPrice()).append("\n");
    }
}
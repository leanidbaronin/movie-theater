package com.jpmc.theater.export;

import com.jpmc.theater.domain.Schedule;

public interface ScheduleFormatter {
    String format(Schedule schedule);
}
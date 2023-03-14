package com.jpmc.theater;

import com.jpmc.theater.domain.Schedule;
import com.jpmc.theater.export.ScheduleConsoleFormatter;
import com.jpmc.theater.export.ScheduleFormatter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApp {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext();
        context.register(SpringConfig.class);
        context.refresh();
        ScheduleFormatter scheduleFormatter = context.getBean(ScheduleConsoleFormatter.class);
        Schedule schedule = context.getBean(Schedule.class);
        System.out.println(scheduleFormatter.format(schedule));
        context.close();
    }
}
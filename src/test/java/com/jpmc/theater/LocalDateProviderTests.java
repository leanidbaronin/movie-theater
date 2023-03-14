package com.jpmc.theater;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class LocalDateProviderTests {

    @Test
    void getCurrentDate_returnsSupplierOutput() {
        int actualYear = 2023;
        int actualMonth = 3;
        int actualDayOfMonth = 13;
        var date = LocalDate.of(actualYear, actualMonth, actualDayOfMonth);
        var dateProvider = new LocalDateProvider(() -> date);

        var actualDate = dateProvider.getCurrentDate();

        assertEquals(LocalDate.of(actualYear, actualMonth, actualDayOfMonth), actualDate);
    }
}
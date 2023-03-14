package com.jpmc.theater;

import java.time.LocalDate;
import java.util.function.Supplier;

public final class LocalDateProvider {

    private final Supplier<LocalDate> localDateSupplier;

    public LocalDateProvider(Supplier<LocalDate> localDateSupplier) {
        this.localDateSupplier = localDateSupplier;
    }

    public LocalDate getCurrentDate() {
        return localDateSupplier.get();
    }
}
package com.jpmc.theater;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class SpringAppIntegrationTests {

    private final ApplicationContext applicationContext;

    SpringAppIntegrationTests(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Test
    void testIfSuccessfullyStarted() {
        assertNotNull(applicationContext.getBean(TheaterReservationService.class));
    }
}
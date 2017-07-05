package com.objectpartners.plummer.reactive_spring5;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

public class FlightEventGenerator {

    private static final AtomicLong COUNTER = new AtomicLong();

    private static final String[] AIRCRAFT = {
            "CESSNA CITATION X",
            "BOMBARDIER C",
            "BOEING 777",
            "BOEING 787",
            "EMBRAER ERJ-175",
            "AIRBUS A380"
    };

    private static final String[] AIRLINE = {
            "UNITED",
            "DELTA",
            "AMERICAN",
            "EMIRATES",
            "BRITISH AIRWAYS"
    };

    public static FlightEvent generateRandomEvent() {
        return generateEventOfType(randomValue(FlightEventType.values()));
    }

    public static FlightEvent generateEventOfType(FlightEventType type) {
        System.out.println("Building FlightEvent...");
        return FlightEvent.builder()
            .aircraft(randomValue(AIRCRAFT))
            .airline(randomValue(AIRLINE))
            .crew(RandomUtils.nextInt(3, 10))
            .passengers(RandomUtils.nextInt(5, 200))
            .destination(RandomStringUtils.randomAlphabetic(3).toUpperCase())
            .origin(RandomStringUtils.randomAlphabetic(3).toUpperCase())
            .flightNumber(RandomUtils.nextInt(100, 10000))
            .id(COUNTER.incrementAndGet())
            .timestamp(Instant.now())
            .type(type)
            .build();
    }

    private static <T> T randomValue(@NotNull T[] values) {
        int randomOrdinal = RandomUtils.nextInt(0, values.length);
        return values[randomOrdinal];
    }
}

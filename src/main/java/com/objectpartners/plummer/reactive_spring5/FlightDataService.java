package com.objectpartners.plummer.reactive_spring5;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface FlightDataService {
    /**
     * Returns a shared {@link Flux} that publishes new {@link FlightEvent}s every 3 seconds
     * @return
     */
    Flux<FlightEvent> getEventFeed();

    /**
     * Returns a {@link Mono} that will resolve upon the next {@link FlightEvent} publication;
     * @return
     */
    Mono<FlightEvent> getNextEvent();

    /**
     * Returns a {@link List} of all {@link FlightEvent}s up until now (limited to the last 1000)
     * @return
     */
    List<FlightEvent> getAllEvents();
}

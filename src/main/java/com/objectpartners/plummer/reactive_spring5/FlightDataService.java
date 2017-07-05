package com.objectpartners.plummer.reactive_spring5;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface FlightDataService {
    Flux<FlightEvent> getEventFeed();

    Mono<FlightEvent> getNextEvent();

    List<FlightEvent> getAllEvents();

    Mono<FlightEvent> getLastEvent();
}

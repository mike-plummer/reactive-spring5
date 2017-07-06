package com.objectpartners.plummer.reactive_spring5;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Service
public class FlightDataServiceImpl implements FlightDataService {

    // Builds a rate-limited Flux that publishes a FlightEvent object every three seconds
    private static final Flux<FlightEvent> EVENT_STREAM = Flux.zip(
            // Build a rate-limiter Flux so we only publish every 3 seconds
            Flux.interval(Duration.of(3, ChronoUnit.SECONDS)),
            // Build a data Flux that builds FlightEvents as often as possible, prevent eager buffering by limiting to 1
            Flux.<FlightEvent>generate(sink -> sink.next(FlightEventGenerator.generateRandomEvent())).limitRate(1),
            // Throw away rate-limiter value since we don't need it, just push FlightEvent into final Flux
            (interval, event) -> event
    ).share();

    /*
    Contains a copy of each event published up to a max of 1000
     */
    private static final Queue<FlightEvent> EVENTS = new CircularFifoQueue<>(1000);

    static {
        // Subscribe to Flux so we can grab a copy of each event as it is published
        EVENT_STREAM.subscribe(event -> {
            System.out.println("Saw an event, adding to archive");
            EVENTS.add(event);
        });
    }

    @Override
    public Flux<FlightEvent> getEventFeed() {
        return EVENT_STREAM;
    }

    @Override
    public Mono<FlightEvent> getNextEvent() {
        return Mono.from(EVENT_STREAM);
    }

    @Override
    public List<FlightEvent> getAllEvents() {
        return new ArrayList<>(EVENTS);
    }
}

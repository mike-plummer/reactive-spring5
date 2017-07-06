package com.objectpartners.plummer.reactive_spring5;

import org.apache.commons.collections4.CollectionUtils;
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
            Flux.interval(Duration.of(3, ChronoUnit.SECONDS)),
            Flux.<FlightEvent>generate(sink -> sink.next(FlightEventGenerator.generateRandomEvent())).limitRate(1),
            (interval, event) -> event
    ).share();

    private static final Queue<FlightEvent> EVENTS = new CircularFifoQueue<>(1000);

    static {
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

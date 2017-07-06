package com.objectpartners.plummer.reactive_spring5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("flightData")
public class FlightEventController {

    @Autowired
    private FlightDataService flightDataService;

    @GetMapping
    public List<FlightEvent> getAll() {
        return flightDataService.getAllEvents();
    }

    @GetMapping("/next")
    public Mono<FlightEvent> getNext() {
        return flightDataService.getNextEvent();
    }

    @GetMapping("/next/{number}")
    public Flux<FlightEvent> nextN(@PathVariable("number") int number) {
        return flightDataService.getEventFeed().take(number);
    }

    @PostMapping("/find")
    public Flux<FlightEvent> getEvents(@RequestBody Mono<List<Long>> idsToFind) {
        // Contrived example but ids that are posted to this controller will be resolved asynchronously
        // The controller method will begin executing before they are deserialized
        return idsToFind
                .flatMapIterable(ids -> ids)
                .map(id -> flightDataService.getAllEvents().stream()
                        .filter(event -> Objects.equals(event.getId(), id))
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull);
    }
}

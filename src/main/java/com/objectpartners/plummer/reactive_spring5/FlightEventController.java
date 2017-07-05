package com.objectpartners.plummer.reactive_spring5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @GetMapping("/last")
    public Mono<FlightEvent> getLast() {
        return flightDataService.getLastEvent();
    }
}

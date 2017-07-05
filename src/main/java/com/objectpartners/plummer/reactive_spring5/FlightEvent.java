package com.objectpartners.plummer.reactive_spring5;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightEvent {
    private Long id;
    private String airline;
    private Integer flightNumber;
    private String origin;
    private String destination;
    private Integer passengers;
    private Integer crew;
    private String aircraft;
    private FlightEventType type;
    private Instant timestamp;
}

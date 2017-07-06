# reactive-spring5
Examples of using new Reactive structures in Spring v5

## How can I use?
Launch the Spring Application. Either:

a. Directly run Application.java from your IDE
b. `gradle bootRun`
    
You can cURL the following URLs to observe various behaviors:

1. `curl http://localhost:8080/flightData` - Get up to 1000 events produced thus far. No Flux or Mono usage by the service.
2. `curl http://localhost:8080/flightData/next` - Get the next event (will block up to 3 seconds). Builds a Mono from a Flux
3. `curl http://localhost:8080/flightData/next/{number}` - Get the next {number} events (will block up to 3 * {number} seconds). Returns a Flux.
4. `curl -X POST http://localhost:8080/flightData/find -H 'content-type: application/json' -d '[1,3,5]'` - Request event ids 1, 3, and 5 by sending as an async request

## License
This code is provided under the terms of the MIT license: basically you're free to do whatever you want with it, but no guarantees are made to its validity, stability, or safety. All works referenced by or utilized by this project are the property of their respective copyright holders and retain licensing that may be more restrictive.
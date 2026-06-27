package com.thomas.airline.flight.controller;

import com.thomas.airline.flight.dto.FlightRequestDto;
import com.thomas.airline.flight.dto.FlightResponseDto;
import com.thomas.airline.flight.entity.Flight;
import com.thomas.airline.flight.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }
    @PostMapping
    public ResponseEntity<FlightResponseDto> createFlight(@Valid @RequestBody FlightRequestDto requestDto){
        FlightResponseDto responseDto=flightService.createFlight(requestDto);
        return ResponseEntity.status(201).body(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<FlightResponseDto>> getAllFlights(){
        List<FlightResponseDto> responseDtos=flightService.getAllFlights();
        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<FlightResponseDto> getFlightById(@PathVariable Long id){
        FlightResponseDto responseDto=flightService.getFlightById(id);
        return ResponseEntity.ok(responseDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<FlightResponseDto> updateFlight(@PathVariable Long id, @Valid @RequestBody FlightRequestDto requestDto){
        FlightResponseDto responseDto=flightService.updateFlight(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id){
        flightService.deleteFlight(id);
        return ResponseEntity.ok("Flight deleted successfully.");
    }
    @GetMapping("/search")
    public ResponseEntity<List<FlightResponseDto>> searchFlights(@RequestParam String sourcecity, @RequestParam String destinationcity, @RequestParam LocalDate travelDate){
        List<FlightResponseDto> flights=flightService.searchFlights(sourcecity,destinationcity,travelDate);
        return ResponseEntity.ok(flights);
    }
}

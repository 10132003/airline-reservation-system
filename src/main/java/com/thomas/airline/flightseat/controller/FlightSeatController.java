package com.thomas.airline.flightseat.controller;


import com.thomas.airline.flightseat.dto.FlightSeatRequestDto;
import com.thomas.airline.flightseat.dto.FlightSeatResponseDto;
import com.thomas.airline.flightseat.service.FlightSeatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flightseats")
public class FlightSeatController {
    private final FlightSeatService flightSeatService;

    public FlightSeatController(FlightSeatService flightSeatService) {
        this.flightSeatService = flightSeatService;
    }
    @PostMapping
    public ResponseEntity<FlightSeatResponseDto> createFlightSeat(@Valid @RequestBody FlightSeatRequestDto requestDto){
        FlightSeatResponseDto responseDto=flightSeatService.createFlightSeat(requestDto);
        return ResponseEntity.status(201).body(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<FlightSeatResponseDto>> getAllFlightSeats(){
        List<FlightSeatResponseDto> responseDtos=flightSeatService.getAllFlightSeats();
        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<FlightSeatResponseDto> getFlightSeatById(@PathVariable Long id){
        FlightSeatResponseDto responseDto=flightSeatService.getFlightSeatById(id);
        return ResponseEntity.ok(responseDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<FlightSeatResponseDto> updateFlightSeat(@PathVariable Long id, @Valid @RequestBody FlightSeatRequestDto requestDto){
        FlightSeatResponseDto responseDto=flightSeatService.updateFlightSeat(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String > deleteFlightSeat(@PathVariable Long id){
        flightSeatService.deleteFlightSeat(id);
        return ResponseEntity.ok("Flight seat deleted successfully.");
    }
}

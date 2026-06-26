package com.thomas.airline.airport.controller;

import com.thomas.airline.airport.dto.AirportRequestDto;
import com.thomas.airline.airport.dto.AirportResponseDto;
import com.thomas.airline.airport.service.AirportService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {
    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }
    @PostMapping
    public ResponseEntity<AirportResponseDto>  createAirport(@Valid @RequestBody AirportRequestDto requestDto){
        AirportResponseDto responseDto=airportService.createAirport(requestDto);
        return ResponseEntity.status(201).body(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<AirportResponseDto>> getAllAirports(){
        List<AirportResponseDto> responseDtos=airportService.getAllAirports();
        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AirportResponseDto> getAirportById(@PathVariable Long id){
        AirportResponseDto responseDto=airportService.getAirportById(id);
        return ResponseEntity.ok(responseDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AirportResponseDto> updateAirport(@PathVariable Long id, @Valid @RequestBody AirportRequestDto requestDto){
        AirportResponseDto responseDto=airportService.updateAirport(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAirport(@PathVariable Long id){
        airportService.deleteAirport(id);
        return ResponseEntity.ok("Airport Deleted Successfully.");
    }
}

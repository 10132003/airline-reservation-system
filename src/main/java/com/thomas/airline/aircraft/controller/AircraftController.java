package com.thomas.airline.aircraft.controller;

import com.thomas.airline.aircraft.dto.AircraftRequestDto;
import com.thomas.airline.aircraft.dto.AircraftResponseDto;
import com.thomas.airline.aircraft.service.AircraftService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aircrafts")
public class AircraftController {
    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }
    @PostMapping
    public ResponseEntity<AircraftResponseDto> createAircraft(@Valid @RequestBody AircraftRequestDto requestDto){
        AircraftResponseDto responseDto=aircraftService.createAircraft(requestDto);
        return ResponseEntity.status(201).body(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<AircraftResponseDto>> getAllAircrafts(){
        List<AircraftResponseDto> responseDto=aircraftService.getAllAircrafts();
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponseDto> getAircraftById(@PathVariable Long id){
        AircraftResponseDto responseDto=aircraftService.getAircraftById(id);
        return ResponseEntity.ok(responseDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AircraftResponseDto> updateAircraft(@PathVariable Long id, @Valid @RequestBody AircraftRequestDto requestDto){
        AircraftResponseDto responseDto=aircraftService.updateAircraft(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAircraft(@PathVariable Long id){
        aircraftService.deleteAircraft(id);
        return ResponseEntity.ok("Aircraft Deleted Successfully.");
    }
}

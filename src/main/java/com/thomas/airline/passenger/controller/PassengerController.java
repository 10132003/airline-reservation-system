package com.thomas.airline.passenger.controller;

import com.thomas.airline.passenger.dto.PassengerRequestDto;
import com.thomas.airline.passenger.dto.PassengerResponseDto;
import com.thomas.airline.passenger.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passengers")
public class PassengerController {
    private  final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }
    @PostMapping
    public ResponseEntity<PassengerResponseDto> createPassenger(@Valid @RequestBody PassengerRequestDto requestDto){
        PassengerResponseDto responseDto=passengerService.createPassenger(requestDto);
        return ResponseEntity.status(201).body(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<PassengerResponseDto>> getAllPassengers(){
        List<PassengerResponseDto> responseDtos= passengerService.getAllPassengers();
        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponseDto> getPassengerById(@PathVariable Long id){
        PassengerResponseDto responseDto= passengerService.getPassengerById(id);
        return ResponseEntity.ok(responseDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponseDto> updatePassenger(@PathVariable Long id, @Valid @RequestBody PassengerRequestDto requestDto){
        PassengerResponseDto responseDto=passengerService.updatePassenger(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String > deletePassenger(@PathVariable Long id){
        passengerService.deletePassenger(id);
        return ResponseEntity.ok("Passenger is deleted successfully.");
    }
}

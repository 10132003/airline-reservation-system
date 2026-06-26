package com.thomas.airline.seat.controller;

import com.thomas.airline.seat.dto.SeatRequestDto;
import com.thomas.airline.seat.dto.SeatResponseDto;
import com.thomas.airline.seat.service.SeatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }
    @PostMapping
    public ResponseEntity<SeatResponseDto> createSeat(@Valid @RequestBody SeatRequestDto requestDto){
        SeatResponseDto responseDto=seatService.createSeat(requestDto);
        return  ResponseEntity.status(201).body(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<SeatResponseDto>> getAllSeats(){
        List<SeatResponseDto> responseDto = seatService.getAllSeats();
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SeatResponseDto> getSeatById(@PathVariable Long id){
        SeatResponseDto responseDto=seatService.getSeatById(id);
        return ResponseEntity.ok(responseDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<SeatResponseDto> updateSeat(@PathVariable Long id, @Valid @RequestBody SeatRequestDto requestDto){
        SeatResponseDto responseDto=seatService.updateSeat(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeat(@PathVariable Long id){
        seatService.deleteSeat(id);
        return ResponseEntity.ok("Seat deleted successfully.");
    }
}

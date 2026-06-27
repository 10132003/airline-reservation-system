package com.thomas.airline.booking.controller;

import com.thomas.airline.booking.dto.BookingRequestDto;
import com.thomas.airline.booking.dto.BookingResponseDto;
import com.thomas.airline.booking.dto.CompleteBookingRequestDto;
import com.thomas.airline.booking.dto.CompleteBookingResponseDto;
import com.thomas.airline.booking.service.BookingService;
import com.thomas.airline.booking.service.BookingWorkflowService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final BookingWorkflowService bookingWorkflowService;

    public BookingController(BookingService bookingService, BookingWorkflowService bookingWorkflowService) {
        this.bookingService = bookingService;
        this.bookingWorkflowService = bookingWorkflowService;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking( @Valid @RequestBody BookingRequestDto requestDto){
        BookingResponseDto responseDto=bookingService.createBooking(requestDto);
        return ResponseEntity.status(201).body(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> getAllBookings(){
        List<BookingResponseDto> responseDtos=bookingService.getAllBookings();
        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable Long id){
        BookingResponseDto responseDto=bookingService.getBookingById(id);
        return ResponseEntity.ok(responseDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDto> updateBooking(@PathVariable Long id, @Valid @RequestBody BookingRequestDto requestDto){
        BookingResponseDto responseDto=bookingService.updateBooking(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String > deleteBooking(@PathVariable Long id){
        bookingService.deleteBooking(id);
        return ResponseEntity.ok("Booking deleted successfully.");
    }
    @PostMapping("/complete")
    public ResponseEntity<CompleteBookingResponseDto> completeBooking(@RequestBody CompleteBookingRequestDto requestDto){
        CompleteBookingResponseDto completeBookingResponseDto=bookingWorkflowService.completeBooking(requestDto);
        return ResponseEntity.ok(completeBookingResponseDto);
    }
}

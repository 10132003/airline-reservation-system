package com.thomas.airline.booking.controller;

import com.thomas.airline.booking.dto.*;
import com.thomas.airline.booking.service.BookingService;
import com.thomas.airline.booking.service.BookingWorkflowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@Tag(
        name = "Booking Management",
        description = "API's to handle the booking requests"
)
public class BookingController {
    private final BookingService bookingService;
    private final BookingWorkflowService bookingWorkflowService;

    public BookingController(BookingService bookingService, BookingWorkflowService bookingWorkflowService) {
        this.bookingService = bookingService;
        this.bookingWorkflowService = bookingWorkflowService;
    }

    @PostMapping
    @Operation(
            summary = "Create new booking",
            description = "Create a new booking and store it in the database"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Booking created successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User or Flight not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<BookingResponseDto> createBooking( @Valid @RequestBody BookingRequestDto requestDto){
        BookingResponseDto responseDto=bookingService.createBooking(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @GetMapping
    @Operation(
            summary = "Retrieve all bookings",
            description = "Retrieve all bookings from the database and display"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "All bookings retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<List<BookingResponseDto>> getAllBookings(){
        List<BookingResponseDto> responseDtos=bookingService.getAllBookings();
        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve booking by ID",
            description = "Retrieve booking by unique ID from the database"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Booking retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Booking not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<BookingResponseDto> getBookingById(@Parameter(
            description = "Unique booking ID",
            example = "1",
            required = true
    ) @PathVariable Long id){
        BookingResponseDto responseDto=bookingService.getBookingById(id);
        return ResponseEntity.ok(responseDto);
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Update the booking",
            description = "Update the booking by unique ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Booking updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Booking, user or flight not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<BookingResponseDto> updateBooking(@Parameter(
            description = "Unique booking ID",
            example = "1",
            required = true
    ) @PathVariable Long id, @Valid @RequestBody BookingRequestDto requestDto){
        BookingResponseDto responseDto=bookingService.updateBooking(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete the booking",
            description = "Delete the booking by unique ID from the database"
    )@ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Booking deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Booking not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<String > deleteBooking(@Parameter(
            description = "Unique booking ID",
            example = "1",
            required = true
    ) @PathVariable Long id){
        bookingService.deleteBooking(id);
        return ResponseEntity.ok("Booking deleted successfully.");
    }
    @PostMapping("/complete")
    @Operation(
            summary = "Complete booking",
            description = "Complete booking lifecycle from booking to notification"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Booking created successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Flight seat not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<CompleteBookingResponseDto> completeBooking(@RequestBody CompleteBookingRequestDto requestDto){
        CompleteBookingResponseDto completeBookingResponseDto=bookingWorkflowService.completeBooking(requestDto);
        return ResponseEntity.ok(completeBookingResponseDto);
    }
    @PostMapping("/cancel")
    @Operation(
            summary = "Cancel the booking",
            description = "Complete lifecycle of booking cancellation"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Booking cancelled successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Flight seat not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<BookingCancellationResponseDto> cancelBooking(@RequestBody BookingCancellationRequestDto requestDto){
        BookingCancellationResponseDto bookingCancellationResponseDto=bookingWorkflowService.cancelBooking(requestDto);
        return ResponseEntity.ok(bookingCancellationResponseDto);
    }
}

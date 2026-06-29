package com.thomas.airline.booking.dto;

import com.thomas.airline.common.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class BookingResponseDto {
    @Schema(
            description = "Unique Booking ID",
            example = "1"
    )
    private Long id;
    @Schema(
            description = "Passenger name record",
            example = "PNR5D6D57"
    )
    private String pnr;
    @Schema(
            description = "Time of the booking done",
            example = "2026-06-28 12:14:52.342451"
    )
    private LocalDateTime bookingTime;
    @Schema(
            description = "Status of the booking",
            example = "CONFIRMED"
    )
    private BookingStatus status;
    @Schema(
            description = "Unique used id",
            example = "1"
    )
    private Long userId;
    @Schema(
            description = "Unique flight id",
            example = "1"
    )
    private Long flightId;

    public BookingResponseDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
}

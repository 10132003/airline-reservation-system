package com.thomas.airline.booking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class BookingCancellationRequestDto {
    @NotNull
    @Schema(
            description = "Unique booking ID",
            example = "1"
    )
    private Long bookingId;

    public BookingCancellationRequestDto() {
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}

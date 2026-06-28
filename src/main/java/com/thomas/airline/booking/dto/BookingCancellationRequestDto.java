package com.thomas.airline.booking.dto;

import jakarta.validation.constraints.NotNull;

public class BookingCancellationRequestDto {
    @NotNull
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

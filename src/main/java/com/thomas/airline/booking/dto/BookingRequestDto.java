package com.thomas.airline.booking.dto;

import jakarta.validation.constraints.NotNull;

public class BookingRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    private Long flightId;

    public BookingRequestDto() {

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

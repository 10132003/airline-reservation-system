package com.thomas.airline.booking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class BookingRequestDto {
    @NotNull
    @Schema(
            description = "Unique user ID",
            example = "1"
    )
    private Long userId;
    @Schema(
            description = "Unique flight ID",
            example = "1"
    )
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

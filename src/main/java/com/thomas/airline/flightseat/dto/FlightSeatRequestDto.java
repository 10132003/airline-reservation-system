package com.thomas.airline.flightseat.dto;

import com.thomas.airline.common.enums.FlightSeatStatus;
import jakarta.validation.constraints.NotNull;

public class FlightSeatRequestDto {
    @NotNull
    private Long flightId;
    @NotNull
    private Long seatId;
    private FlightSeatStatus status;

    public FlightSeatRequestDto() {
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public FlightSeatStatus getStatus() {
        return status;
    }

    public void setStatus(FlightSeatStatus status) {
        this.status = status;
    }
}

package com.thomas.airline.flightseat.dto;

import com.thomas.airline.common.enums.FlightSeatStatus;

public class FlightSeatResponseDto {
    private Long id;
    private Long flightId;
    private Long seatId;
    private FlightSeatStatus status;

    public FlightSeatResponseDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

package com.thomas.airline.ticket.dto;

import jakarta.validation.constraints.NotNull;

public class TicketRequestDto {
    @NotNull
    private Long bookingId;
    @NotNull
    private Long passengerId;
    @NotNull
    private Long flightSeatId;

    public TicketRequestDto() {

    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public Long getFlightSeatId() {
        return flightSeatId;
    }

    public void setFlightSeatId(Long flightSeatId) {
        this.flightSeatId = flightSeatId;
    }
}

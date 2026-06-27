package com.thomas.airline.ticket.dto;

import com.thomas.airline.common.enums.TicketStatus;

public class TicketResponseDto {
    private Long id;
    private String ticketNumber;
    private TicketStatus status;
    private Long bookingId;
    private Long passengerId;
    private Long flightSeatId;

    public TicketResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
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

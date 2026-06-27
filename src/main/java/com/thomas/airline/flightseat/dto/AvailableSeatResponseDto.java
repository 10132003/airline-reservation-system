package com.thomas.airline.flightseat.dto;

import com.thomas.airline.common.enums.SeatClass;
import com.thomas.airline.common.enums.SeatType;

public class AvailableSeatResponseDto {

    private Long flightSeatId;
    private String seatNumber;
    private SeatClass seatClass;
    private SeatType seatType;

    public AvailableSeatResponseDto() {
    }

    public Long getFlightSeatId() {
        return flightSeatId;
    }

    public void setFlightSeatId(Long flightSeatId) {
        this.flightSeatId = flightSeatId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }
}
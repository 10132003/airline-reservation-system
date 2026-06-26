package com.thomas.airline.seat.dto;

import com.thomas.airline.common.enums.SeatClass;
import com.thomas.airline.common.enums.SeatType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SeatRequestDto {
    @NotBlank
    private String seatNumber;
    @NotNull
    private SeatClass seatClass;
    @NotNull
    private SeatType seatType;
    @NotNull
    private Long aircraftId;

    public SeatRequestDto() {
    }

    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}

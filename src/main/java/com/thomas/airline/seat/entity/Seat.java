package com.thomas.airline.seat.entity;

import com.thomas.airline.aircraft.entity.Aircraft;
import com.thomas.airline.common.enums.SeatClass;
import com.thomas.airline.common.enums.SeatType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String seatNumber;
    @Enumerated(EnumType.STRING)
    @NotNull
    private SeatClass seatClass;
    @Enumerated(EnumType.STRING)
    @NotNull
    private SeatType seatType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_id",nullable = false)
    private Aircraft aircraft;

    public Seat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }
}

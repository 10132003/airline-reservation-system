package com.thomas.airline.flightseat.entity;

import com.thomas.airline.common.enums.FlightSeatStatus;
import com.thomas.airline.flight.entity.Flight;
import com.thomas.airline.seat.entity.Seat;
import jakarta.persistence.*;


@Entity
@Table(name = "flight_seat")
public class FlightSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id",nullable = false)
    private Seat seat;
    @Enumerated(EnumType.STRING)
    private FlightSeatStatus status=FlightSeatStatus.AVAILABLE;

    public FlightSeat() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public FlightSeatStatus getStatus() {
        return status;
    }

    public void setStatus(FlightSeatStatus status) {
        this.status = status;
    }
}

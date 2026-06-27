package com.thomas.airline.ticket.entity;

import com.thomas.airline.booking.entity.Booking;
import com.thomas.airline.common.enums.TicketStatus;
import com.thomas.airline.flightseat.entity.FlightSeat;
import com.thomas.airline.passenger.entity.Passenger;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique = true,nullable = false)
    private String ticketNumber;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="booking_id",nullable = false)
    private Booking booking;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id",nullable = false,unique = true)
    private Passenger passenger;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flightseat_id",nullable = false,unique = true)
    private FlightSeat flightSeat;

    public Ticket() {
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

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public FlightSeat getFlightSeat() {
        return flightSeat;
    }

    public void setFlightSeat(FlightSeat flightSeat) {
        this.flightSeat = flightSeat;
    }
}

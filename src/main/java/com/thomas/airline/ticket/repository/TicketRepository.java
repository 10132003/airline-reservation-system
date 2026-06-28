package com.thomas.airline.ticket.repository;

import com.thomas.airline.flight.entity.Flight;
import com.thomas.airline.flightseat.entity.FlightSeat;
import com.thomas.airline.passenger.entity.Passenger;
import com.thomas.airline.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    Optional<Ticket> findByTicketNumber(String ticketNumber);
    boolean existsByTicketNumber(String ticketNumber);
    Optional<Ticket> findByPassenger(Passenger passenger);
    Optional<Ticket> findByFlightSeat(FlightSeat flightSeat);
    boolean existsByPassenger(Passenger passenger);
    boolean existsByFlightSeat(FlightSeat flightSeat);
    Optional<Ticket> findByBookingId(Long bookingId);
}

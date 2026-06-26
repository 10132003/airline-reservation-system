package com.thomas.airline.flightseat.repository;

import com.thomas.airline.flight.entity.Flight;
import com.thomas.airline.flightseat.entity.FlightSeat;
import com.thomas.airline.seat.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightSeatRepository extends JpaRepository<FlightSeat,Long> {
    boolean existsByFlightAndSeat(Flight flight, Seat seat);

    Optional<FlightSeat> findByFlightAndSeat(Flight flight, Seat seat);
}

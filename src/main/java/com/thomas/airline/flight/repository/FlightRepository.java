package com.thomas.airline.flight.repository;

import com.thomas.airline.flight.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight,Long> {
    boolean existsByFlightNumber(String flightNumber);
    Optional<Flight> findByFlightNumber(String flightNumber);
}

package com.thomas.airline.flight.repository;

import com.thomas.airline.airport.entity.Airport;
import com.thomas.airline.flight.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight,Long> {
    boolean existsByFlightNumber(String flightNumber);
    Optional<Flight> findByFlightNumber(String flightNumber);
    List<Flight> findBySourceAirportAndDestinationAirportAndDepartureTimeBetween(Airport sourceAirport, Airport destinationAirport, LocalDateTime startOfDay, LocalDateTime endOfDay);
}

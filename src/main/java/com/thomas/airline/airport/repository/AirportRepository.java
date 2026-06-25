package com.thomas.airline.airport.repository;

import com.thomas.airline.airport.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport,Long> {
    boolean existsByAirportCode(String airportCode);
    Optional<Airport> findByAirportCode(String airportCode);
}

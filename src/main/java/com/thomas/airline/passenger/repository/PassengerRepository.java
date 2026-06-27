package com.thomas.airline.passenger.repository;

import com.thomas.airline.passenger.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger,Long> {
    boolean existsByPassportNumber(String passportNumber);
    Optional<Passenger> findByPassportNumber(String  passportNumber);
}

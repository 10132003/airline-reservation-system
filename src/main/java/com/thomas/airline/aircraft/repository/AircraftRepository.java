package com.thomas.airline.aircraft.repository;

import com.thomas.airline.aircraft.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft,Long> {
    boolean existsByRegistrationNumber(String registrationNumber);
    Optional<Aircraft> findByRegistrationNumber(String registrationNumber);
}

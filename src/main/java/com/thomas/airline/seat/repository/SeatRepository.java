package com.thomas.airline.seat.repository;

import com.thomas.airline.aircraft.entity.Aircraft;
import com.thomas.airline.seat.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    boolean existsBySeatNumberAndAircraft(String seatNumber, Aircraft aircraft);
    Optional<Seat> findBySeatNumberAndAircraft(String seatNumber, Aircraft aircraft);

}

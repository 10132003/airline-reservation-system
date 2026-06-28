package com.thomas.airline.booking.repository;

import com.thomas.airline.booking.entity.Booking;
import com.thomas.airline.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking , Long> {
    boolean existsByPnr(String pnr);
    Optional<Booking> findByPnr(String pnr);

}

package com.thomas.airline.payment.repository;

import com.thomas.airline.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    boolean existsByTransactionId(String transactionId);
    Optional<Payment> findByTransactionId(String transactionId);
    boolean existsByBookingId(Long bookingId);
    Optional<Payment> findByBookingId(Long bookingId);
}

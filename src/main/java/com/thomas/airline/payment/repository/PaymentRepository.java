package com.thomas.airline.payment.repository;

import com.thomas.airline.common.enums.PaymentStatus;
import com.thomas.airline.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    boolean existsByTransactionId(String transactionId);
    Optional<Payment> findByTransactionId(String transactionId);
    boolean existsByBookingId(Long bookingId);
    Optional<Payment> findByBookingId(Long bookingId);
    @Query("""
       SELECT SUM(p.amount)
       FROM Payment p
       WHERE p.paymentStatus = :paymentStatus
       """)
    BigDecimal getTotalRevenue(@Param("paymentStatus") PaymentStatus paymentStatus);
}

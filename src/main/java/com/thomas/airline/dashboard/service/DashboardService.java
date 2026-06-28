package com.thomas.airline.dashboard.service;

import com.thomas.airline.booking.dto.BookingResponseDto;
import com.thomas.airline.booking.entity.Booking;
import com.thomas.airline.booking.repository.BookingRepository;
import com.thomas.airline.booking.service.BookingService;
import com.thomas.airline.common.enums.BookingStatus;
import com.thomas.airline.common.enums.FlightSeatStatus;
import com.thomas.airline.common.enums.PaymentStatus;
import com.thomas.airline.dashboard.dto.DashboardResponseDto;
import com.thomas.airline.flight.repository.FlightRepository;
import com.thomas.airline.flight.service.FlightService;
import com.thomas.airline.flightseat.repository.FlightSeatRepository;
import com.thomas.airline.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class DashboardService {
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final FlightSeatRepository flightSeatRepository;
    private final PaymentRepository paymentRepository;
    private final FlightService flightService;
    private final BookingService bookingService;

    public DashboardService(FlightRepository flightRepository, BookingRepository bookingRepository, FlightSeatRepository flightSeatRepository, PaymentRepository paymentRepository, FlightService flightService, BookingService bookingService) {
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
        this.flightSeatRepository = flightSeatRepository;
        this.paymentRepository = paymentRepository;
        this.flightService=flightService;
        this.bookingService = bookingService;
    }
    public Long getTotalFlights() {
        return flightRepository.count();
    }
    public Long getTotalBookings(){
        return bookingRepository.count();
    }
    public Long getConfirmedBookings(){
        return bookingRepository.countByStatus(BookingStatus.CONFIRMED);
    }
    public Long getCancelledBookings(){
        return bookingRepository.countByStatus(BookingStatus.CANCELLED);
    }
    public Long getTodayBookings(){
        LocalDate todayDate= LocalDate.now();
        LocalDateTime startDate=todayDate.atStartOfDay();
        LocalDateTime endDate=todayDate.plusDays(1).atStartOfDay();
        return bookingRepository.countByBookingTimeBetween(startDate,endDate);
    }
    public Long getAvailableSeats(){
        return flightSeatRepository.countByStatus(FlightSeatStatus.AVAILABLE);
    }
    public BigDecimal getTotalRevenue(){
        BigDecimal revenue= paymentRepository.getTotalRevenue(PaymentStatus.SUCCESS);
        return revenue!=null? revenue:BigDecimal.ZERO;
    }
    public DashboardResponseDto getDashboardStatistics(){
        DashboardResponseDto responseDto=new DashboardResponseDto();
        responseDto.setTotalFlights(getTotalFlights());
        responseDto.setTodayBookings(getTodayBookings());
        responseDto.setTotalBookings(getTotalBookings());
        responseDto.setConfirmedBookings(getConfirmedBookings());
        responseDto.setCancelledBookings(getCancelledBookings());
        responseDto.setAvailableSeats(getAvailableSeats());
        responseDto.setTotalRevenue(getTotalRevenue());
        return responseDto;
    }
}

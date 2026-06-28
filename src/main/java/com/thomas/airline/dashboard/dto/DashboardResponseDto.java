package com.thomas.airline.dashboard.dto;

import java.math.BigDecimal;

public class DashboardResponseDto {
    private Long totalFlights;
    private Long totalBookings;
    private Long confirmedBookings;
    private Long cancelledBookings;
    private Long todayBookings;
    private Long availableSeats;
    private BigDecimal totalRevenue;

    public DashboardResponseDto() {

    }

    public Long getTotalFlights() {
        return totalFlights;
    }

    public void setTotalFlights(Long totalFlights) {
        this.totalFlights = totalFlights;
    }

    public Long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(Long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public Long getConfirmedBookings() {
        return confirmedBookings;
    }

    public void setConfirmedBookings(Long confirmedBookings) {
        this.confirmedBookings = confirmedBookings;
    }

    public Long getCancelledBookings() {
        return cancelledBookings;
    }

    public void setCancelledBookings(Long cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }

    public Long getTodayBookings() {
        return todayBookings;
    }

    public void setTodayBookings(Long todayBookings) {
        this.todayBookings = todayBookings;
    }

    public Long getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Long availableSeats) {
        this.availableSeats = availableSeats;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}

package com.thomas.airline.booking.dto;

import com.thomas.airline.common.enums.BookingStatus;
import com.thomas.airline.common.enums.PaymentStatus;

public class BookingCancellationResponseDto {
    private Long bookingId;
    private String pnr;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;
    private String message="Booking cancelled successfully";

    public BookingCancellationResponseDto() {

    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

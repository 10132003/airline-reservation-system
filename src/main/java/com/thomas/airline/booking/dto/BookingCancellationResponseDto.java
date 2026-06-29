package com.thomas.airline.booking.dto;

import com.thomas.airline.common.enums.BookingStatus;
import com.thomas.airline.common.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public class BookingCancellationResponseDto {
    @Schema(
            description = "Unique booking id",
            example = "1"
    )
    private Long bookingId;
    @Schema(
            description = "Passenger name record",
            example = "PNR1C84A5"
    )
    private String pnr;
    @Schema(
            description = "Booking status",
            example = "PENDING"
    )
    private BookingStatus bookingStatus;
    @Schema(
            description = "Payment status of the booking",
            example = "CONFIRMED"
    )
    private PaymentStatus paymentStatus;
    @Schema(
            description = "Message after the booking"
    )
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

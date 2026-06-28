package com.thomas.airline.booking.mapper;

import com.thomas.airline.booking.dto.BookingRequestDto;
import com.thomas.airline.booking.dto.CompleteBookingRequestDto;
import com.thomas.airline.common.enums.NotificationType;
import com.thomas.airline.notification.dto.NotificationRequestDto;
import com.thomas.airline.notification.dto.NotificationResponseDto;
import com.thomas.airline.passenger.dto.PassengerRequestDto;
import com.thomas.airline.payment.dto.PaymentRequestDto;
import com.thomas.airline.ticket.dto.TicketRequestDto;
import com.thomas.airline.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class BookingWorkflowMapper {

    public BookingRequestDto toBookingRequestDto(CompleteBookingRequestDto requestDto) {
        BookingRequestDto bookingRequestDto = new BookingRequestDto();
        bookingRequestDto.setUserId(requestDto.getUserId());
        bookingRequestDto.setFlightId(requestDto.getFlightId());
        return bookingRequestDto;
    }

    public PassengerRequestDto toPassengerRequestDto(CompleteBookingRequestDto requestDto, Long bookingId) {
        PassengerRequestDto passengerRequestDto = new PassengerRequestDto();
        passengerRequestDto.setFirstName(requestDto.getFirstName());
        passengerRequestDto.setLastName(requestDto.getLastName());
        passengerRequestDto.setAge(requestDto.getAge());
        passengerRequestDto.setGender(requestDto.getGender());
        passengerRequestDto.setPassportNumber(requestDto.getPassportNumber());
        passengerRequestDto.setBookingId(bookingId);
        return passengerRequestDto;
    }

    public PaymentRequestDto toPaymentRequestDto(CompleteBookingRequestDto requestDto, Long bookingId) {
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setBookingId(bookingId);
        paymentRequestDto.setAmount(requestDto.getAmount());
        paymentRequestDto.setPaymentMethod(requestDto.getPaymentMethod());
        return paymentRequestDto;
    }
    public TicketRequestDto toTicketRequestDto(Long bookingId, Long passengerId, Long flightSeatId) {
        TicketRequestDto ticketRequestDto = new TicketRequestDto();
        ticketRequestDto.setBookingId(bookingId);
        ticketRequestDto.setPassengerId(passengerId);
        ticketRequestDto.setFlightSeatId(flightSeatId);
        return ticketRequestDto;
    }
    public NotificationRequestDto toNotificationRequestDto(Long userId, String pnr, String ticketNumber) {
        NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
        notificationRequestDto.setUserId(userId);
        notificationRequestDto.setTitle("Booking Confirmed");
        notificationRequestDto.setMessage("Your booking has been confirmed. PNR: " + pnr + ", Ticket Number: " + ticketNumber);
        notificationRequestDto.setNotificationType(NotificationType.BOOKING_CONFIRMATION);

        return notificationRequestDto;
    }
    public NotificationRequestDto NotificationRequestDto(Long id,String pnr){
        NotificationRequestDto notificationRequestDto=new NotificationRequestDto();
        notificationRequestDto.setUserId(id);
        notificationRequestDto.setNotificationType(NotificationType.BOOKING_CONFIRMATION);
        notificationRequestDto.setTitle("Booking Confirmed");
        notificationRequestDto.setMessage("Your booking with PNR " + pnr + " has been confirmed.");
        return notificationRequestDto;
    }
}
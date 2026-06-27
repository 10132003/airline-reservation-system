package com.thomas.airline.booking.service;

import com.thomas.airline.booking.dto.BookingRequestDto;
import com.thomas.airline.booking.dto.BookingResponseDto;
import com.thomas.airline.booking.dto.CompleteBookingRequestDto;
import com.thomas.airline.booking.dto.CompleteBookingResponseDto;
import com.thomas.airline.booking.entity.Booking;
import com.thomas.airline.booking.mapper.BookingWorkflowMapper;
import com.thomas.airline.common.enums.PaymentStatus;
import com.thomas.airline.flightseat.dto.FlightSeatResponseDto;
import com.thomas.airline.flightseat.service.FlightSeatService;
import com.thomas.airline.notification.dto.NotificationRequestDto;
import com.thomas.airline.notification.service.NotificationService;
import com.thomas.airline.passenger.dto.PassengerRequestDto;
import com.thomas.airline.passenger.dto.PassengerResponseDto;
import com.thomas.airline.passenger.service.PassengerService;
import com.thomas.airline.payment.dto.PaymentRequestDto;
import com.thomas.airline.payment.dto.PaymentResponseDto;
import com.thomas.airline.payment.entity.Payment;
import com.thomas.airline.payment.service.PaymentService;
import com.thomas.airline.ticket.dto.TicketRequestDto;
import com.thomas.airline.ticket.dto.TicketResponseDto;
import com.thomas.airline.ticket.service.TicketService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BookingWorkflowService {
    private final BookingService bookingService;
    private final PassengerService passengerService;
    private final FlightSeatService flightSeatService;
    private final PaymentService paymentService;
    private final TicketService ticketService;
    private final NotificationService notificationService;
    private final BookingWorkflowMapper bookingWorkflowMapper;
    public BookingWorkflowService(BookingService bookingService, PassengerService passengerService, FlightSeatService flightSeatService, PaymentService paymentService, TicketService ticketService, NotificationService notificationService, BookingWorkflowMapper bookingWorkflowMapper) {
        this.bookingService = bookingService;
        this.passengerService = passengerService;
        this.flightSeatService = flightSeatService;
        this.paymentService = paymentService;
        this.ticketService = ticketService;
        this.notificationService = notificationService;
        this.bookingWorkflowMapper=bookingWorkflowMapper;
    }
    public CompleteBookingResponseDto completeBooking(CompleteBookingRequestDto requestDto) {

        // Create Booking
        BookingRequestDto bookingRequestDto = bookingWorkflowMapper.toBookingRequestDto(requestDto);
        BookingResponseDto bookingResponseDto = bookingService.createBooking(bookingRequestDto);

        Long bookingId = bookingResponseDto.getId();
        String pnr = bookingResponseDto.getPnr();

        // Create Passenger
        PassengerRequestDto passengerRequestDto =
                bookingWorkflowMapper.toPassengerRequestDto(requestDto, bookingId);

        PassengerResponseDto passengerResponseDto =
                passengerService.createPassenger(passengerRequestDto);

        Long passengerId = passengerResponseDto.getId();

        // Reserve Flight Seat
        FlightSeatResponseDto flightSeatResponseDto =
                flightSeatService.reserveFlightSeat(requestDto.getFlightSeatId());

        // Create Payment
        PaymentRequestDto paymentRequestDto =
                bookingWorkflowMapper.toPaymentRequestDto(requestDto, bookingId);

        PaymentResponseDto paymentResponseDto =
                paymentService.createPayment(paymentRequestDto);

        // Create Ticket
        TicketRequestDto ticketRequestDto =
                bookingWorkflowMapper.toTicketRequestDto(
                        bookingId,
                        passengerId,
                        requestDto.getFlightSeatId()
                );

        TicketResponseDto ticketResponseDto =
                ticketService.createTicket(ticketRequestDto);

        // Create Notification
        NotificationRequestDto notificationRequestDto =
                bookingWorkflowMapper.toNotificationRequestDto(
                        requestDto.getUserId(),
                        pnr,
                        ticketResponseDto.getTicketNumber()
                );

        notificationService.createNotification(notificationRequestDto);

        // Prepare Response
        CompleteBookingResponseDto completeBookingResponseDto =
                new CompleteBookingResponseDto();

        completeBookingResponseDto.setBookingId(bookingId);
        completeBookingResponseDto.setPnr(pnr);
        completeBookingResponseDto.setBookingStatus(bookingResponseDto.getStatus());
        completeBookingResponseDto.setPaymentStatus(paymentResponseDto.getPaymentStatus());
        completeBookingResponseDto.setTransactionId(paymentResponseDto.getTransactionId());
        completeBookingResponseDto.setTicketNumber(ticketResponseDto.getTicketNumber());

        return completeBookingResponseDto;
    }
}

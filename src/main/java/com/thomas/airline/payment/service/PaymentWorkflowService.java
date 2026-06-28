package com.thomas.airline.payment.service;

import com.thomas.airline.booking.dto.BookingRequestDto;
import com.thomas.airline.booking.dto.BookingResponseDto;
import com.thomas.airline.booking.mapper.BookingWorkflowMapper;
import com.thomas.airline.booking.repository.BookingRepository;
import com.thomas.airline.booking.service.BookingService;
import com.thomas.airline.common.enums.BookingStatus;
import com.thomas.airline.common.enums.PaymentStatus;
import com.thomas.airline.exception.BookingAlreadyCompletedException;
import com.thomas.airline.exception.PaymentAlreadyCompletedException;
import com.thomas.airline.notification.dto.NotificationRequestDto;
import com.thomas.airline.notification.service.NotificationService;
import com.thomas.airline.payment.dto.PaymentResponseDto;
import com.thomas.airline.payment.dto.PaymentSuccessRequestDto;
import com.thomas.airline.payment.dto.PaymentSuccessResponseDto;
import com.thomas.airline.payment.entity.Payment;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PaymentWorkflowService {
    private final PaymentService paymentService;
    private final BookingService bookingService;
    private final NotificationService notificationService;
    private final BookingWorkflowMapper bookingWorkflowMapper;

    public PaymentWorkflowService(PaymentService paymentService, BookingService bookingService, NotificationService notificationService, BookingWorkflowMapper bookingWorkflowMapper) {
        this.paymentService = paymentService;
        this.bookingService = bookingService;
        this.notificationService = notificationService;
        this.bookingWorkflowMapper=bookingWorkflowMapper;
    }
    public PaymentSuccessResponseDto paymentSuccess(PaymentSuccessRequestDto paymentSuccessRequestDto){
        Long paymentId=paymentSuccessRequestDto.getPaymentId();
        PaymentResponseDto paymentResponseDto=paymentService.getPaymentById(paymentId);
        if(!paymentResponseDto.getPaymentStatus().equals(PaymentStatus.PENDING)){
            throw new PaymentAlreadyCompletedException("Payment is already completed.");
        }
        PaymentResponseDto paymentResponseDto1=paymentService.updatePaymentStatus(paymentId,PaymentStatus.SUCCESS);
        Long paymentId1=paymentResponseDto1.getId();
        String transactionId=paymentResponseDto1.getTransactionId();
        PaymentStatus paymentStatus=paymentResponseDto1.getPaymentStatus();
        Long bookingId=paymentResponseDto1.getBookingId();
        BookingResponseDto bookingResponseDto=bookingService.getBookingById(bookingId);
        if(!bookingResponseDto.getStatus().equals(BookingStatus.PENDING)){
            throw  new BookingAlreadyCompletedException("Booking is already completed.");
        }
        BookingResponseDto bookingResponseDto1=bookingService.updateBookingStatus(bookingId,BookingStatus.CONFIRMED);
        Long userId = bookingResponseDto1.getUserId();
        String pnr = bookingResponseDto1.getPnr();
        NotificationRequestDto notificationRequestDto = bookingWorkflowMapper.NotificationRequestDto(userId, pnr);
        notificationService.createNotification(notificationRequestDto);
        Long bookingId1=bookingResponseDto1.getId();
        BookingStatus bookingStatus=bookingResponseDto1.getStatus();
        PaymentSuccessResponseDto paymentSuccessResponseDto=new PaymentSuccessResponseDto();
        paymentSuccessResponseDto.setPaymentId(paymentId1);
        paymentSuccessResponseDto.setBookingId(bookingId1);
        paymentSuccessResponseDto.setTransactionId(transactionId);
        paymentSuccessResponseDto.setPaymentStatus(paymentStatus);
        paymentSuccessResponseDto.setPnr(pnr);
        paymentSuccessResponseDto.setBookingStatus(bookingStatus);
        paymentSuccessResponseDto.setMessage("Your payment was successful. Your booking with PNR " + pnr + " has been confirmed.");
        return paymentSuccessResponseDto;
    }
}

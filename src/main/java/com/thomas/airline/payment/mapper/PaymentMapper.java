package com.thomas.airline.payment.mapper;

import com.thomas.airline.payment.dto.PaymentRequestDto;
import com.thomas.airline.payment.dto.PaymentResponseDto;
import com.thomas.airline.payment.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public Payment requestDtoToPayment(PaymentRequestDto requestDto){
        Payment payment=new Payment();
        payment.setAmount(requestDto.getAmount());
        payment.setPaymentMethod(requestDto.getPaymentMethod());
        return payment;
    }
    public PaymentResponseDto paymentToResponseDto(Payment payment){
        PaymentResponseDto responseDto=new PaymentResponseDto();
        responseDto.setId(payment.getId());
        responseDto.setTransactionId(payment.getTransactionId());
        responseDto.setAmount(payment.getAmount());
        responseDto.setPaymentMethod(payment.getPaymentMethod());
        responseDto.setPaymentStatus(payment.getPaymentStatus());
        responseDto.setPaymentTime(payment.getPaymentTime());
        responseDto.setBookingId(payment.getBooking().getId());
        return responseDto;
    }
}

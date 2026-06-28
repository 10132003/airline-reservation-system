package com.thomas.airline.payment.dto;

import jakarta.validation.constraints.NotNull;

public class PaymentSuccessRequestDto {
    @NotNull(message = "Payment id is  required.")
    private Long paymentId;

    public PaymentSuccessRequestDto() {
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
}

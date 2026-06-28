package com.thomas.airline.exception;

public class PaymentAlreadyCompletedException extends RuntimeException{
    public PaymentAlreadyCompletedException(String message){
        super(message);
    }
}

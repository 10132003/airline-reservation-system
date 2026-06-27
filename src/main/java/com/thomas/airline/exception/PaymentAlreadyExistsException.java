package com.thomas.airline.exception;

public class PaymentAlreadyExistsException extends RuntimeException{
    public PaymentAlreadyExistsException(String message){
        super(message);
    }
}

package com.thomas.airline.exception;

public class BookingAlreadyCompletedException extends RuntimeException{
    public BookingAlreadyCompletedException(String message){
        super(message);
    }
}

package com.thomas.airline.exception;

public class BookingNotFoundException extends RuntimeException{
    public  BookingNotFoundException(String message){
        super(message);
    }
}

package com.thomas.airline.exception;

public class FlightSeatNotFoundException extends RuntimeException{
    public FlightSeatNotFoundException(String message){
        super(message);
    }
}

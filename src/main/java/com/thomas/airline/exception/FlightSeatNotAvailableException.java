package com.thomas.airline.exception;

public class FlightSeatNotAvailableException extends RuntimeException{
    public FlightSeatNotAvailableException(String message){
        super(message);
    }
}

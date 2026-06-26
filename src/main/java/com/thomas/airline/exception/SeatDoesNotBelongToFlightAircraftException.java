package com.thomas.airline.exception;

public class SeatDoesNotBelongToFlightAircraftException extends RuntimeException{
    public SeatDoesNotBelongToFlightAircraftException(String message){
        super(message);
    }
}

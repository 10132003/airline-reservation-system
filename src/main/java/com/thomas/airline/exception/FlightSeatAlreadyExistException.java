package com.thomas.airline.exception;

public class FlightSeatAlreadyExistException extends RuntimeException{
    public FlightSeatAlreadyExistException(String message){
        super(message);
    }
}

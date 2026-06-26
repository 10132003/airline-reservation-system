package com.thomas.airline.exception;

public class FlightAlreadyExistException extends RuntimeException{
    public FlightAlreadyExistException(String  message){
        super(message);
    }
}

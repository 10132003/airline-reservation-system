package com.thomas.airline.exception;

public class AirportAlreadyExistException extends RuntimeException{
    public AirportAlreadyExistException(String message){
        super(message);
    }
}

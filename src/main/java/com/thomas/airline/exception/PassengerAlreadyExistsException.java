package com.thomas.airline.exception;

public class PassengerAlreadyExistsException extends RuntimeException{
    public PassengerAlreadyExistsException(String  message){
        super(message);
    }
}

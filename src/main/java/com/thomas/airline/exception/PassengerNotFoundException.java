package com.thomas.airline.exception;

public class PassengerNotFoundException extends RuntimeException{
    public PassengerNotFoundException(String message){
        super(message);
    }
}

package com.thomas.airline.exception;

public class AircraftNotFoundException extends RuntimeException{
    public AircraftNotFoundException(String message){
        super(message);
    }
}

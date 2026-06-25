package com.thomas.airline.exception;

public class AirportNotFoundException  extends RuntimeException{
    public AirportNotFoundException(String message){
        super(message);
    }
}

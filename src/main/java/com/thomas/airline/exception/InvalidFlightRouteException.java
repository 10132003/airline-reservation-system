package com.thomas.airline.exception;

public class InvalidFlightRouteException extends RuntimeException{
    public InvalidFlightRouteException(String  message){
        super(message);
    }
}

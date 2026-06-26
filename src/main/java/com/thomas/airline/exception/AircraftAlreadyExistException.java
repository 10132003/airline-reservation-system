package com.thomas.airline.exception;

public class AircraftAlreadyExistException extends  RuntimeException{
    public AircraftAlreadyExistException(String message){
        super(message);
    }
}

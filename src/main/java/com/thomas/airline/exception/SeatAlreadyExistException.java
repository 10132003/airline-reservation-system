package com.thomas.airline.exception;

public class SeatAlreadyExistException extends RuntimeException{
    public SeatAlreadyExistException(String message){
        super(message);
    }
}

package com.thomas.airline.exception;

public class TicketAlreadyExistException extends RuntimeException{
    public TicketAlreadyExistException(String message){
        super(message);
    }
}

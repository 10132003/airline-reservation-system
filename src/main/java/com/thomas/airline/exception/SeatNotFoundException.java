package com.thomas.airline.exception;

public class SeatNotFoundException  extends  RuntimeException{
    public SeatNotFoundException(String message){
        super(message);
    }
}

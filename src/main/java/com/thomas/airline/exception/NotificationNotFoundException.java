package com.thomas.airline.exception;

public class NotificationNotFoundException extends  RuntimeException{
    public NotificationNotFoundException(String message){
        super(message);
    }
}

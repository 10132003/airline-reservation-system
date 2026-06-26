package com.thomas.airline.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler (UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExist(UserAlreadyExistException ex){
        return ResponseEntity.status(409).body(ex.getMessage());
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(AirportAlreadyExistException.class)
    public ResponseEntity<String > handleAirportAlreadyExist(AirportAlreadyExistException ex){
        return ResponseEntity.status(409).body(ex.getMessage());
    }
    @ExceptionHandler(AirportNotFoundException.class)
    public ResponseEntity<String > handleAirportNotFound(AirportNotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(AircraftAlreadyExistException.class)
    public ResponseEntity<String> handleAircraftAlreadyExist(AircraftAlreadyExistException ex){
        return ResponseEntity.status(409).body(ex.getMessage());
    }
    @ExceptionHandler(AircraftNotFoundException.class)
    public ResponseEntity<String > handleAircraftNotFound(AircraftNotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(SeatAlreadyExistException.class)
    public ResponseEntity<String > handleSeatAlreadyExist(SeatAlreadyExistException ex){
        return ResponseEntity.status(409).body(ex.getMessage());
    }
    @ExceptionHandler(SeatNotFoundException.class)
    public ResponseEntity<String>handleSeatNotFound(SeatNotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}

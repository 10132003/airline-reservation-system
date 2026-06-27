package com.thomas.airline.exception;

import com.thomas.airline.payment.repository.PaymentRepository;
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
    @ExceptionHandler(FlightAlreadyExistException.class)
    public ResponseEntity<String > handleFlightAlreadyExist(FlightAlreadyExistException ex){
        return ResponseEntity.status(409).body(ex.getMessage());
    }
    @ExceptionHandler(InvalidFlightRouteException.class)
    public ResponseEntity<String> handleInvalidFlightRoute(InvalidFlightRouteException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(InvalidFlightTimeException.class)
    public ResponseEntity<String >handleInvalidFlightTime(InvalidFlightTimeException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<String> handleFlightNotFound(FlightNotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(FlightSeatAlreadyExistException.class)
    public ResponseEntity<String > handleFlightSeatAlreadyExist(FlightSeatAlreadyExistException ex){
        return ResponseEntity.status(409).body(ex.getMessage());
    }
    @ExceptionHandler(SeatDoesNotBelongToFlightAircraftException.class)
    public ResponseEntity<String > handleSeatDoesNotBelongToFlightAircraft(SeatDoesNotBelongToFlightAircraftException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(FlightSeatNotFoundException.class)
    public ResponseEntity<String> handleFlightSeatNotFound(FlightSeatNotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<String> handleBookingNotFound(BookingNotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(PassengerAlreadyExistsException.class)
    public ResponseEntity<String> handlePassengerAlreadyExist(PassengerAlreadyExistsException ex){
        return ResponseEntity.status(409).body(ex.getMessage());
    }
    @ExceptionHandler(PassengerNotFoundException.class)
    public ResponseEntity<String > handlePassengerNotFound(PassengerNotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(PaymentAlreadyExistsException.class)
    public ResponseEntity<String > handlePaymentAlreadyExist(PaymentAlreadyExistsException ex){
        return ResponseEntity.status(409).body(ex.getMessage());
    }
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<String> handlePaymentNotFound(PaymentNotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}

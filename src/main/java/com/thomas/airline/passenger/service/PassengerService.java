package com.thomas.airline.passenger.service;

import com.thomas.airline.booking.entity.Booking;
import com.thomas.airline.booking.repository.BookingRepository;
import com.thomas.airline.exception.BookingNotFoundException;
import com.thomas.airline.exception.PassengerAlreadyExistsException;
import com.thomas.airline.exception.PassengerNotFoundException;
import com.thomas.airline.passenger.dto.PassengerRequestDto;
import com.thomas.airline.passenger.dto.PassengerResponseDto;
import com.thomas.airline.passenger.entity.Passenger;
import com.thomas.airline.passenger.mapper.PassengerMapper;
import com.thomas.airline.passenger.repository.PassengerRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final PassengerMapper passengerMapper;

    public PassengerService(PassengerRepository passengerRepository, BookingRepository bookingRepository, PassengerMapper passengerMapper) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.passengerMapper = passengerMapper;
    }
    public PassengerResponseDto createPassenger(PassengerRequestDto requestDto){
        Booking booking=bookingRepository.findById(requestDto.getBookingId()).orElseThrow(()-> new BookingNotFoundException("Booking is not available."));
        if(passengerRepository.existsByPassportNumber(requestDto.getPassportNumber())){
            throw new PassengerAlreadyExistsException("Passenger with this passport number already exists.");
        }
        Passenger passenger=passengerMapper.requestDtoToPassenger(requestDto);
        passenger.setBooking(booking);
        Passenger savedPassenger=passengerRepository.save(passenger);
        PassengerResponseDto responseDto=passengerMapper.passengerToResponseDto(savedPassenger);
        return responseDto;
    }
    public List<PassengerResponseDto> getAllPassengers(){
        List<Passenger> passengers=passengerRepository.findAll();
        List<PassengerResponseDto> responseDtos=new ArrayList<>();
        for(Passenger passenger:passengers){
            PassengerResponseDto responseDto=passengerMapper.passengerToResponseDto(passenger);
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
    public PassengerResponseDto getPassengerById(Long id){
        Passenger passenger=passengerRepository.findById(id).orElseThrow(()-> new PassengerNotFoundException("Passenger not found with id: " + id));
        PassengerResponseDto responseDto=passengerMapper.passengerToResponseDto(passenger);
        return responseDto;
    }
    public PassengerResponseDto updatePassenger(Long id, PassengerRequestDto requestDto){
        Passenger passenger=passengerRepository.findById(id).orElseThrow(()-> new PassengerNotFoundException("Passenger not found with id: " + id));
        Booking booking=bookingRepository.findById(requestDto.getBookingId()).orElseThrow(()-> new BookingNotFoundException("Booking is not available."));
        Optional<Passenger> existPassportNumber=passengerRepository.findByPassportNumber(requestDto.getPassportNumber());
        if(existPassportNumber.isPresent() && !existPassportNumber.get().getId().equals(id)){
            throw new PassengerAlreadyExistsException("Passenger with this passport number already exists.");
        }
        passenger.setFirstName(requestDto.getFirstName());
        passenger.setLastName(requestDto.getLastName());
        passenger.setAge(requestDto.getAge());
        passenger.setGender(requestDto.getGender());
        passenger.setPassportNumber(requestDto.getPassportNumber());
        passenger.setBooking(booking);
        Passenger savedPassenger=passengerRepository.save(passenger);
        PassengerResponseDto responseDto=passengerMapper.passengerToResponseDto(savedPassenger);
        return responseDto;
    }
    public void deletePassenger(Long id){
        Passenger passenger=passengerRepository.findById(id).orElseThrow(()-> new PassengerNotFoundException("Passenger is not available."));
        passengerRepository.delete(passenger);
    }
}

package com.thomas.airline.passenger.mapper;

import com.thomas.airline.passenger.dto.PassengerRequestDto;
import com.thomas.airline.passenger.dto.PassengerResponseDto;
import com.thomas.airline.passenger.entity.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {
    public Passenger requestDtoToPassenger(PassengerRequestDto requestDto){
        Passenger passenger=new Passenger();
        passenger.setFirstName(requestDto.getFirstName());
        passenger.setLastName(requestDto.getLastName());
        passenger.setAge(requestDto.getAge());
        passenger.setGender(requestDto.getGender());
        passenger.setPassportNumber(requestDto.getPassportNumber());
        return passenger;
    }
    public PassengerResponseDto passengerToResponseDto(Passenger passenger){
        PassengerResponseDto responseDto=new PassengerResponseDto();
        responseDto.setId(passenger.getId());
        responseDto.setFirstName(passenger.getFirstName());
        responseDto.setLastName(passenger.getLastName());
        responseDto.setAge(passenger.getAge());
        responseDto.setGender(passenger.getGender());
        responseDto.setPassportNumber(passenger.getPassportNumber());
        responseDto.setBookingId(passenger.getBooking().getId());
        return responseDto;
    }
}

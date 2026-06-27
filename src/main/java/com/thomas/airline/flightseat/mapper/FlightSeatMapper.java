package com.thomas.airline.flightseat.mapper;

import com.thomas.airline.flightseat.dto.AvailableSeatResponseDto;
import com.thomas.airline.flightseat.dto.FlightSeatRequestDto;
import com.thomas.airline.flightseat.dto.FlightSeatResponseDto;
import com.thomas.airline.flightseat.entity.FlightSeat;
import org.springframework.stereotype.Component;

@Component
public class FlightSeatMapper {
    public FlightSeat requestDtoToFlightSeat(FlightSeatRequestDto requestDto){
        FlightSeat flightSeat=new FlightSeat();
        flightSeat.setStatus(requestDto.getStatus());
        return flightSeat;
    }
    public FlightSeatResponseDto flightSeatToResponseDto(FlightSeat flightSeat){
        FlightSeatResponseDto responseDto=new FlightSeatResponseDto();
        responseDto.setId(flightSeat.getId());
        responseDto.setFlightId(flightSeat.getFlight().getId());
        responseDto.setSeatId(flightSeat.getSeat().getId());
        responseDto.setStatus(flightSeat.getStatus());
        return  responseDto;
    }
    public AvailableSeatResponseDto flightSeatToAvailableSeatResponseDto(FlightSeat flightSeat){
        AvailableSeatResponseDto availableSeatResponseDto=new AvailableSeatResponseDto();
        availableSeatResponseDto.setFlightSeatId(flightSeat.getId());
        availableSeatResponseDto.setSeatNumber(flightSeat.getSeat().getSeatNumber());
        availableSeatResponseDto.setSeatClass(flightSeat.getSeat().getSeatClass());
        availableSeatResponseDto.setSeatType(flightSeat.getSeat().getSeatType());
        return availableSeatResponseDto;
    }
}

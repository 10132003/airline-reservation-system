package com.thomas.airline.flight.mapper;

import com.thomas.airline.flight.dto.FlightRequestDto;
import com.thomas.airline.flight.dto.FlightResponseDto;
import com.thomas.airline.flight.entity.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper  {
    public Flight requestDtoToFlight(FlightRequestDto requestDto){
        Flight flight=new Flight();
        flight.setFlightNumber(requestDto.getFlightNumber());
        flight.setDepartureTime(requestDto.getDepartureTime());
        flight.setArrivalTime(requestDto.getArrivalTime());
        flight.setBasePrice(requestDto.getBasePrice());
        return flight;
    }
    public FlightResponseDto flightToResponseDto(Flight flight){
        FlightResponseDto responseDto=new FlightResponseDto();
        responseDto.setId(flight.getId());
        responseDto.setFlightNumber(flight.getFlightNumber());
        responseDto.setDepartureTime(flight.getDepartureTime());
        responseDto.setArrivalTime(flight.getArrivalTime());
        responseDto.setBasePrice(flight.getBasePrice());
        responseDto.setStatus(flight.getStatus());
        responseDto.setSourceAirportId(flight.getSourceAirport().getId());
        responseDto.setDestinationAirportId(flight.getDestinationAirport().getId());
        responseDto.setAircraftId(flight.getAircraft().getId());
        return responseDto;
    }
}

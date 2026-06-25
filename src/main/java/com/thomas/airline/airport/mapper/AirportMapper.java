package com.thomas.airline.airport.mapper;

import com.thomas.airline.airport.dto.AirportRequestDto;
import com.thomas.airline.airport.dto.AirportResponseDto;
import com.thomas.airline.airport.entity.Airport;
import org.springframework.stereotype.Component;

@Component
public class AirportMapper {
    public Airport requestDtoToAirport(AirportRequestDto requestDto){
        Airport airport=new Airport();
        airport.setAirportCode(requestDto.getAirportCode());
        airport.setAirportName(requestDto.getAirportName());
        airport.setCity(requestDto.getCity());
        airport.setState(requestDto.getState());
        airport.setCountry(requestDto.getCountry());
        airport.setTimezone(requestDto.getTimezone());
        return airport;
    }
    public AirportResponseDto airportToResponseDto(Airport airport){
        AirportResponseDto responseDto=new AirportResponseDto();
        responseDto.setId(airport.getId());
        responseDto.setAirportCode(airport.getAirportCode());
        responseDto.setAirportName(airport.getAirportName());
        responseDto.setCity(airport.getCity());
        responseDto.setState(airport.getState());
        responseDto.setCountry(airport.getCountry());
        responseDto.setTimezone(airport.getTimezone());
        responseDto.setStatus(airport.getStatus());
        responseDto.setCreatedAt(airport.getCreatedAt());
        responseDto.setUpdatedAt(airport.getUpdatedAt());
        return responseDto;
    }
}

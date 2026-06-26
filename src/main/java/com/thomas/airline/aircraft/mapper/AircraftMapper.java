package com.thomas.airline.aircraft.mapper;

import com.thomas.airline.aircraft.dto.AircraftRequestDto;
import com.thomas.airline.aircraft.dto.AircraftResponseDto;
import com.thomas.airline.aircraft.entity.Aircraft;
import org.springframework.stereotype.Component;

@Component
public class AircraftMapper {
    public Aircraft requestDtoToAircraft(AircraftRequestDto requestDto){
        Aircraft aircraft=new Aircraft();
        aircraft.setRegistrationNumber(requestDto.getRegistrationNumber());
        aircraft.setManufacturer(requestDto.getManufacturer());
        aircraft.setModel(requestDto.getModel());
        return aircraft;
    }
    public AircraftResponseDto aircraftToResponseDto(Aircraft aircraft){
        AircraftResponseDto responseDto=new AircraftResponseDto();
        responseDto.setId(aircraft.getId());
        responseDto.setRegistrationNumber(aircraft.getRegistrationNumber());
        responseDto.setManufacturer(aircraft.getManufacturer());
        responseDto.setModel(aircraft.getModel());
        responseDto.setStatus(aircraft.getStatus());
        responseDto.setCreatedAt(aircraft.getCreatedAt());
        responseDto.setUpdatedAt(aircraft.getUpdatedAt());
        return responseDto;
    }
}

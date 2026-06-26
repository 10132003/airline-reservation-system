package com.thomas.airline.aircraft.service;

import com.thomas.airline.aircraft.dto.AircraftRequestDto;
import com.thomas.airline.aircraft.dto.AircraftResponseDto;
import com.thomas.airline.aircraft.entity.Aircraft;
import com.thomas.airline.aircraft.mapper.AircraftMapper;
import com.thomas.airline.aircraft.repository.AircraftRepository;
import com.thomas.airline.common.enums.AircraftStatus;
import com.thomas.airline.exception.AircraftAlreadyExistException;
import com.thomas.airline.exception.AircraftNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AircraftService {
    private final AircraftRepository aircraftRepository;
    private final AircraftMapper aircraftMapper;

    public AircraftService(AircraftRepository aircraftRepository, AircraftMapper aircraftMapper) {
        this.aircraftRepository = aircraftRepository;
        this.aircraftMapper = aircraftMapper;
    }
    public AircraftResponseDto createAircraft(AircraftRequestDto requestDto){
        if(aircraftRepository.existsByRegistrationNumber(requestDto.getRegistrationNumber())){
            throw  new AircraftAlreadyExistException("Aircraft is already in use.");
        }
        Aircraft aircraft=aircraftMapper.requestDtoToAircraft(requestDto);
        aircraft.setStatus(AircraftStatus.ACTIVE);
        aircraft.setCreatedAt(LocalDateTime.now());
        aircraft.setUpdatedAt(LocalDateTime.now());
        Aircraft savedAircraft=aircraftRepository.save(aircraft);
        AircraftResponseDto responseDto=aircraftMapper.aircraftToResponseDto(savedAircraft);
        return responseDto;
    }
    public List<AircraftResponseDto> getAllAircrafts(){
        List<Aircraft> aircrafts=aircraftRepository.findAll();
        List<AircraftResponseDto> responseDtos=new ArrayList<>();
        for(Aircraft aircraft:aircrafts){
            AircraftResponseDto responseDto=aircraftMapper.aircraftToResponseDto(aircraft);
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
    public AircraftResponseDto getAircraftById(Long id){
        Aircraft aircraft=aircraftRepository.findById(id).orElseThrow(()-> new AircraftNotFoundException("Aircraft is not available."));
        AircraftResponseDto responseDto=aircraftMapper.aircraftToResponseDto(aircraft);
        return responseDto;
    }
    public AircraftResponseDto updateAircraft(Long id,AircraftRequestDto requestDto){
        Aircraft aircraft=aircraftRepository.findById(id).orElseThrow(()->new AircraftNotFoundException("Aircraft is not available."));
        if(!aircraft.getRegistrationNumber().equals(requestDto.getRegistrationNumber())){
            if(aircraftRepository.existsByRegistrationNumber(requestDto.getRegistrationNumber())){
                throw new AircraftAlreadyExistException("Aircraft is already in use.");
            }
            aircraft.setRegistrationNumber(requestDto.getRegistrationNumber());
        }
        aircraft.setModel(requestDto.getModel());
        aircraft.setManufacturer(requestDto.getManufacturer());
        aircraft.setUpdatedAt(LocalDateTime.now());
        Aircraft savedAircraft=aircraftRepository.save(aircraft);
        AircraftResponseDto responseDto=aircraftMapper.aircraftToResponseDto(savedAircraft);
        return responseDto;
    }
    public void deleteAircraft(Long id){
        Aircraft aircraft=aircraftRepository.findById(id).orElseThrow(()-> new AircraftNotFoundException("Aircraft is not available."));
        aircraft.setStatus(AircraftStatus.INACTIVE);
        aircraft.setUpdatedAt(LocalDateTime.now());
        aircraftRepository.save(aircraft);
    }
}

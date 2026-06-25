package com.thomas.airline.airport.service;

import com.thomas.airline.airport.dto.AirportRequestDto;
import com.thomas.airline.airport.dto.AirportResponseDto;
import com.thomas.airline.airport.entity.Airport;
import com.thomas.airline.airport.mapper.AirportMapper;
import com.thomas.airline.airport.repository.AirportRepository;
import com.thomas.airline.common.enums.AirportStatus;
import com.thomas.airline.exception.AirportAlreadyExistException;
import com.thomas.airline.exception.AirportNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AirportService {
    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;

    public AirportService(AirportRepository airportRepository, AirportMapper airportMapper) {
        this.airportRepository = airportRepository;
        this.airportMapper = airportMapper;
    }
    public AirportResponseDto createAirport(AirportRequestDto requestDto){
        if(airportRepository.existsByAirportCode(requestDto.getAirportCode())){
            throw  new AirportAlreadyExistException("Airport is already available.");
        }
        Airport airport=airportMapper.requestDtoToAirport(requestDto);
        airport.setStatus(AirportStatus.ACTIVE);
        LocalDateTime currentTime=LocalDateTime.now();
        airport.setCreatedAt(currentTime);
        airport.setUpdatedAt(currentTime);
        Airport savedAirport=airportRepository.save(airport);
        AirportResponseDto responseDto=airportMapper.airportToResponseDto(savedAirport);
        return responseDto;
    }
    public List<AirportResponseDto> getAllAirports(){
        List<Airport> airports=airportRepository.findAll();
        List<AirportResponseDto> responseDtos=new ArrayList<>();
        for(Airport airport:airports){
            AirportResponseDto responseDto=airportMapper.airportToResponseDto(airport);
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
    public AirportResponseDto getAirportById(Long id){
        Airport airport=airportRepository.findById(id).orElseThrow(()->new AirportNotFoundException("Airport is not available."));
        AirportResponseDto responseDto=airportMapper.airportToResponseDto(airport);
        return responseDto;
    }
    public AirportResponseDto updateAirport(Long id, AirportRequestDto requestDto){
        Airport airport=airportRepository.findById(id).orElseThrow(()->new AirportNotFoundException("Airport is not available."));
        if(!airport.getAirportCode().equals(requestDto.getAirportCode())){
            if(airportRepository.existsByAirportCode(requestDto.getAirportCode())){
                throw  new AirportAlreadyExistException("The airport is already available.");
            }
            airport.setAirportCode(requestDto.getAirportCode());
        }
        airport.setAirportName(requestDto.getAirportName());
        airport.setCity(requestDto.getCity());
        airport.setState(requestDto.getState());
        airport.setCountry(requestDto.getCountry());
        airport.setTimezone(requestDto.getTimezone());
        airport.setUpdatedAt(LocalDateTime.now());
        Airport savedAirport=airportRepository.save(airport);
        AirportResponseDto responseDto=airportMapper.airportToResponseDto(savedAirport);
        return responseDto;
    }
    public void deleteAirport(Long id){
        Airport airport=airportRepository.findById(id).orElseThrow(()-> new AirportNotFoundException("Airport is not available."));
        airport.setStatus(AirportStatus.INACTIVE);
        airport.setUpdatedAt(LocalDateTime.now());
        airportRepository.save(airport);
    }
}

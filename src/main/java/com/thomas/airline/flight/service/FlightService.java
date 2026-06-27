package com.thomas.airline.flight.service;

import com.thomas.airline.aircraft.entity.Aircraft;
import com.thomas.airline.aircraft.repository.AircraftRepository;
import com.thomas.airline.airport.entity.Airport;
import com.thomas.airline.airport.repository.AirportRepository;
import com.thomas.airline.common.enums.FlightStatus;
import com.thomas.airline.exception.*;
import com.thomas.airline.flight.dto.FlightRequestDto;
import com.thomas.airline.flight.dto.FlightResponseDto;
import com.thomas.airline.flight.entity.Flight;
import com.thomas.airline.flight.mapper.FlightMapper;
import com.thomas.airline.flight.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;
    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;

    public FlightService(FlightRepository flightRepository, FlightMapper flightMapper, AircraftRepository aircraftRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
        this.aircraftRepository = aircraftRepository;
        this.airportRepository = airportRepository;
    }
    public FlightResponseDto createFlight(FlightRequestDto requestDto){
        if(flightRepository.existsByFlightNumber(requestDto.getFlightNumber())){
            throw new FlightAlreadyExistException("Flight is already available.");
        }
        Airport sourceAirport=airportRepository.findById(requestDto.getSourceAirportId()).orElseThrow(()-> new AirportNotFoundException("Source airport not found."));
        Airport destinationAirport=airportRepository.findById(requestDto.getDestinationAirportId()).orElseThrow(()-> new AirportNotFoundException("Destination airport not found."));
        Aircraft aircraft=aircraftRepository.findById(requestDto.getAircraftId()).orElseThrow(()->new AircraftNotFoundException("Aircraft is not available."));
        if(sourceAirport.getId().equals(destinationAirport.getId())){
            throw new InvalidFlightRouteException("Both Source and Destination cannot be same.");
        }
        if(!requestDto.getDepartureTime().isBefore(requestDto.getArrivalTime())){
            throw  new InvalidFlightTimeException("Departure time must be before arrival time");
        }
        Flight flight=flightMapper.requestDtoToFlight(requestDto);
        flight.setSourceAirport(sourceAirport);
        flight.setDestinationAirport(destinationAirport);
        flight.setAircraft(aircraft);
        flight.setStatus(FlightStatus.SCHEDULED);
        Flight savedFlight=flightRepository.save(flight);
        FlightResponseDto responseDto=flightMapper.flightToResponseDto(savedFlight);
        return responseDto;
    }
    public List<FlightResponseDto> getAllFlights(){
        List<Flight> flights=flightRepository.findAll();
        List<FlightResponseDto> responseDtos=new ArrayList<>();
        for(Flight flight:flights){
            FlightResponseDto responseDto=flightMapper.flightToResponseDto(flight);
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
    public FlightResponseDto getFlightById(Long id){
        Flight flight=flightRepository.findById(id).orElseThrow(()-> new FlightNotFoundException("Flight is not available."));
        FlightResponseDto responseDto=flightMapper.flightToResponseDto(flight);
        return responseDto;
    }
    public FlightResponseDto updateFlight(Long id, FlightRequestDto requestDto){
        Flight flight=flightRepository.findById(id).orElseThrow(()->new FlightNotFoundException("Flight is not available."));
        Optional<Flight> existingFlight = flightRepository.findByFlightNumber(requestDto.getFlightNumber());
        if (existingFlight.isPresent() && !existingFlight.get().getId().equals(flight.getId())) {
            throw new FlightAlreadyExistException("Flight is already available.");
        }
        Airport sourceAirport= airportRepository.findById(requestDto.getSourceAirportId()).orElseThrow(()-> new AirportNotFoundException("Source airport not found."));
        Airport destinationAirport=airportRepository.findById((requestDto.getDestinationAirportId())).orElseThrow(()-> new AirportNotFoundException("Destination airport not found."));
        Aircraft aircraft=aircraftRepository.findById(requestDto.getAircraftId()).orElseThrow(()-> new AircraftNotFoundException("Aircraft is not available."));
        if(sourceAirport.getId().equals(destinationAirport.getId())){
            throw  new InvalidFlightRouteException("Both Source and Destination cannot be same.");
        }
        if(!requestDto.getDepartureTime().isBefore(requestDto.getArrivalTime())){
            throw new InvalidFlightTimeException("Departure time must be before arrival time");
        }
        flight.setSourceAirport(sourceAirport);
        flight.setDestinationAirport(destinationAirport);
        flight.setAircraft(aircraft);
        flight.setFlightNumber(requestDto.getFlightNumber());
        flight.setDepartureTime(requestDto.getDepartureTime());
        flight.setArrivalTime(requestDto.getArrivalTime());
        flight.setBasePrice(requestDto.getBasePrice());
        Flight savedFlight=flightRepository.save(flight);
        FlightResponseDto responseDto=flightMapper.flightToResponseDto(savedFlight);
        return responseDto;
    }
    public void deleteFlight(Long id){
        Flight flight=flightRepository.findById(id).orElseThrow(()-> new FlightNotFoundException("Flight is not available."));
        flightRepository.delete(flight);
    }
    public List<FlightResponseDto> searchFlights(String sourcecity, String destinationcity, LocalDate travelDate){
         Airport sourceAirport=airportRepository.findByCityIgnoreCase(sourcecity).orElseThrow(()-> new CityNotFoundException("City is not available."));
         Airport destinationAirport=airportRepository.findByCityIgnoreCase(destinationcity).orElseThrow(()-> new CityNotFoundException("City is not available."));
         LocalDateTime startOfDay=travelDate.atStartOfDay();
         LocalDateTime endOfDay=travelDate.atTime(LocalTime.MAX);
         List<Flight> flights=flightRepository.findBySourceAirportAndDestinationAirportAndDepartureTimeBetween(sourceAirport,destinationAirport,startOfDay,endOfDay);
         List<FlightResponseDto> responseDtos=new ArrayList<>();
        for(Flight flight:flights){
            FlightResponseDto responseDto=flightMapper.flightToResponseDto(flight);
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
}

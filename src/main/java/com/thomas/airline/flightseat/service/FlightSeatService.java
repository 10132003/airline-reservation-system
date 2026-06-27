package com.thomas.airline.flightseat.service;

import com.thomas.airline.common.enums.FlightSeatStatus;
import com.thomas.airline.exception.*;
import com.thomas.airline.flight.entity.Flight;
import com.thomas.airline.flight.mapper.FlightMapper;
import com.thomas.airline.flight.repository.FlightRepository;
import com.thomas.airline.flightseat.dto.AvailableSeatResponseDto;
import com.thomas.airline.flightseat.dto.FlightSeatRequestDto;
import com.thomas.airline.flightseat.dto.FlightSeatResponseDto;
import com.thomas.airline.flightseat.entity.FlightSeat;
import com.thomas.airline.flightseat.mapper.FlightSeatMapper;
import com.thomas.airline.flightseat.repository.FlightSeatRepository;
import com.thomas.airline.seat.entity.Seat;
import com.thomas.airline.seat.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightSeatService {
    private final FlightSeatRepository flightSeatRepository;
    private final FlightSeatMapper flightSeatMapper;
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;

    public FlightSeatService(FlightSeatRepository flightSeatRepository, FlightSeatMapper flightSeatMapper, FlightRepository flightRepository, SeatRepository seatRepository) {
        this.flightSeatRepository = flightSeatRepository;
        this.flightSeatMapper = flightSeatMapper;
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
    }

    public FlightSeatResponseDto createFlightSeat(FlightSeatRequestDto requestDto){
        Flight flight=flightRepository.findById(requestDto.getFlightId()).orElseThrow(()->new FlightNotFoundException("Flight is not available."));
        Seat seat=seatRepository.findById(requestDto.getSeatId()).orElseThrow(()-> new SeatNotFoundException("Seat is not available."));
        if(!seat.getAircraft().getId().equals(flight.getAircraft().getId())){
        throw new SeatDoesNotBelongToFlightAircraftException("Wrong seat.");
        }
        if(flightSeatRepository.existsByFlightAndSeat(flight,seat)){
            throw new FlightSeatAlreadyExistException("Seat is already available.");
        }
        FlightSeat flightSeat= flightSeatMapper.requestDtoToFlightSeat(requestDto);
        flightSeat.setFlight(flight);
        flightSeat.setSeat(seat);
        flightSeat.setStatus(FlightSeatStatus.AVAILABLE);
        FlightSeat savedFlightSeat=flightSeatRepository.save(flightSeat);
        FlightSeatResponseDto responseDto=flightSeatMapper.flightSeatToResponseDto(savedFlightSeat);
        return responseDto;
    }
    public List<FlightSeatResponseDto> getAllFlightSeats(){
        List<FlightSeat> flightSeats=flightSeatRepository.findAll();
        List<FlightSeatResponseDto> responseDtos=new ArrayList<>();
        for(FlightSeat flightSeat:flightSeats){
            FlightSeatResponseDto responseDto=flightSeatMapper.flightSeatToResponseDto(flightSeat);
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
    public FlightSeatResponseDto getFlightSeatById(Long id){
        FlightSeat flightSeat=flightSeatRepository.findById(id).orElseThrow(()-> new FlightSeatNotFoundException("Flight seat is not available."));
        FlightSeatResponseDto responseDto=flightSeatMapper.flightSeatToResponseDto(flightSeat);
        return responseDto;
    }
    public FlightSeatResponseDto updateFlightSeat(Long id, FlightSeatRequestDto requestDto){
        FlightSeat flightSeat=flightSeatRepository.findById(id).orElseThrow(()-> new FlightSeatNotFoundException("Flight seat is not available."));
        Flight flight=flightRepository.findById(requestDto.getFlightId()).orElseThrow(()->new FlightNotFoundException("Flight is not available."));
        Seat seat=seatRepository.findById(requestDto.getSeatId()).orElseThrow(()-> new SeatNotFoundException("Seat is not available."));
        if(!seat.getAircraft().getId().equals(flight.getAircraft().getId())){
            throw new SeatDoesNotBelongToFlightAircraftException("Wrong seat.");
        }
        Optional<FlightSeat> existingFlightSeat = flightSeatRepository.findByFlightAndSeat(flight, seat);
        if (existingFlightSeat.isPresent() && !existingFlightSeat.get().getId().equals(id)) {
            throw new FlightSeatAlreadyExistException("Seat is already assigned to this flight.");
        }
        flightSeat.setFlight(flight);
        flightSeat.setSeat(seat);
        flightSeat.setStatus(FlightSeatStatus.AVAILABLE);
        FlightSeat savedFlightSeat=flightSeatRepository.save(flightSeat);
        FlightSeatResponseDto responseDto=flightSeatMapper.flightSeatToResponseDto(savedFlightSeat);
        return responseDto;
    }
    public void deleteFlightSeat(Long id){
        FlightSeat flightSeat=flightSeatRepository.findById(id).orElseThrow(()-> new FlightSeatNotFoundException("Flight seat is not available."));
        flightSeatRepository.delete(flightSeat);
    }
    public List<AvailableSeatResponseDto> getAvailableSeats(Long flightId){
        Flight flight=flightRepository.findById(flightId).orElseThrow(()->new FlightNotFoundException("Flight is not available."));
        List<FlightSeat> flightSeats=flightSeatRepository.findByFlightAndStatus(flight,FlightSeatStatus.AVAILABLE);
        List<AvailableSeatResponseDto> responseDtos=new ArrayList<>();
        for (FlightSeat flightSeat:flightSeats){
            AvailableSeatResponseDto availableSeatResponseDto=flightSeatMapper.flightSeatToAvailableSeatResponseDto(flightSeat);
            responseDtos.add(availableSeatResponseDto);
        }
        return responseDtos;
    }
}
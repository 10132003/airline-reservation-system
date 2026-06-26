package com.thomas.airline.seat.service;

import com.thomas.airline.aircraft.entity.Aircraft;
import com.thomas.airline.aircraft.repository.AircraftRepository;
import com.thomas.airline.exception.AircraftNotFoundException;
import com.thomas.airline.exception.SeatAlreadyExistException;
import com.thomas.airline.exception.SeatNotFoundException;
import com.thomas.airline.seat.dto.SeatRequestDto;
import com.thomas.airline.seat.dto.SeatResponseDto;
import com.thomas.airline.seat.entity.Seat;
import com.thomas.airline.seat.mapper.SeatMapper;
import com.thomas.airline.seat.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;
    private final AircraftRepository aircraftRepository;

    public SeatService(SeatRepository seatRepository, SeatMapper seatMapper, AircraftRepository aircraftRepository) {
        this.seatRepository = seatRepository;
        this.seatMapper = seatMapper;
        this.aircraftRepository = aircraftRepository;
    }
    public SeatResponseDto createSeat(SeatRequestDto requestDto){
        Aircraft aircraft=aircraftRepository.findById(requestDto.getAircraftId()).orElseThrow(()-> new AircraftNotFoundException("Aircraft is not available."));
        if(seatRepository.existsBySeatNumberAndAircraft(requestDto.getSeatNumber(),aircraft)){
            throw  new SeatAlreadyExistException("Seat already exist.");
        }
        Seat seat=seatMapper.requestDtoToSeat(requestDto);
        seat.setAircraft(aircraft);
        Seat savedSeat=seatRepository.save(seat);
        SeatResponseDto responseDto=seatMapper.seatToResponseDto(savedSeat);
        return responseDto;
    }
    public List<SeatResponseDto> getAllSeats(){
        List<Seat> seats=seatRepository.findAll();
        List<SeatResponseDto> responseDtos=new ArrayList<>();
        for(Seat seat:seats){
            SeatResponseDto responseDto=seatMapper.seatToResponseDto(seat);
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
    public SeatResponseDto getSeatById(Long id){
        Seat seat=seatRepository.findById(id).orElseThrow(()-> new SeatNotFoundException("Seat is not available."));
        SeatResponseDto responseDto=seatMapper.seatToResponseDto(seat);
        return responseDto;
    }
    public SeatResponseDto updateSeat(Long id, SeatRequestDto requestDto){
        Seat seat=seatRepository.findById(id).orElseThrow(()-> new SeatNotFoundException("Seat is not available."));
        Aircraft aircraft=aircraftRepository.findById(requestDto.getAircraftId()).orElseThrow(()-> new AircraftNotFoundException("Aircraft is not available."));
        Optional<Seat> existingSeat = seatRepository.findBySeatNumberAndAircraft(requestDto.getSeatNumber(), aircraft);
        if (existingSeat.isPresent() && !existingSeat.get().getId().equals(seat.getId())) {
            throw new SeatAlreadyExistException("Seat already exist.");
        }
        seat.setSeatNumber(requestDto.getSeatNumber());
        seat.setSeatClass(requestDto.getSeatClass());
        seat.setSeatType(requestDto.getSeatType());
        seat.setAircraft(aircraft);
        Seat savedSeat=seatRepository.save(seat);
        SeatResponseDto responseDto=seatMapper.seatToResponseDto(savedSeat);
        return responseDto;
    }
    public void deleteSeat(Long id){
        Seat seat=seatRepository.findById(id).orElseThrow(()-> new SeatNotFoundException("Seat is not available."));
        seatRepository.delete(seat);
    }
}

package com.thomas.airline.seat.mapper;

import com.thomas.airline.seat.dto.SeatRequestDto;
import com.thomas.airline.seat.dto.SeatResponseDto;
import com.thomas.airline.seat.entity.Seat;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {
    public Seat requestDtoToSeat(SeatRequestDto requestDto){
        Seat seat= new Seat();
        seat.setSeatNumber(requestDto.getSeatNumber());
        seat.setSeatClass(requestDto.getSeatClass());
        seat.setSeatType(requestDto.getSeatType());
        return seat;
    }
    public SeatResponseDto seatToResponseDto(Seat seat){
        SeatResponseDto responseDto=new SeatResponseDto();
        responseDto.setId(seat.getId());
        responseDto.setSeatNumber(seat.getSeatNumber());
        responseDto.setSeatClass(seat.getSeatClass());
        responseDto.setSeatType(seat.getSeatType());
        responseDto.setAircraftId(seat.getAircraft().getId());
        return responseDto;
    }
}

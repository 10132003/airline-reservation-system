package com.thomas.airline.booking.mapper;

import com.thomas.airline.booking.dto.BookingRequestDto;
import com.thomas.airline.booking.dto.BookingResponseDto;
import com.thomas.airline.booking.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public Booking requestDtoToBooking(BookingRequestDto requestDto){
        Booking booking=new Booking();
        return booking;
    }
    public BookingResponseDto bookingToResponseDto(Booking booking){
        BookingResponseDto responseDto=new BookingResponseDto();
        responseDto.setId(booking.getId());
        responseDto.setPnr(booking.getPnr());
        responseDto.setBookingTime(booking.getBookingTime());
        responseDto.setStatus(booking.getStatus());
        responseDto.setUserId(booking.getUser().getId());
        responseDto.setFlightId(booking.getFlight().getId());
        return responseDto;
    }
}

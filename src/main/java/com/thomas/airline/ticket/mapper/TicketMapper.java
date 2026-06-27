package com.thomas.airline.ticket.mapper;

import com.thomas.airline.ticket.dto.TicketRequestDto;
import com.thomas.airline.ticket.dto.TicketResponseDto;
import com.thomas.airline.ticket.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public Ticket requestDtoToTicket(TicketRequestDto requestDto){
        Ticket ticket=new Ticket();
        return ticket;
    }
    public TicketResponseDto ticketToResponseDto(Ticket ticket){
        TicketResponseDto responseDto=new TicketResponseDto();
        responseDto.setId(ticket.getId());
        responseDto.setTicketNumber(ticket.getTicketNumber());
        responseDto.setStatus(ticket.getStatus());
        responseDto.setBookingId(ticket.getBooking().getId());
        responseDto.setPassengerId(ticket.getPassenger().getId());
        responseDto.setFlightSeatId(ticket.getFlightSeat().getId());
        return responseDto;
    }
}

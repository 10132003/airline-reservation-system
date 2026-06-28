package com.thomas.airline.ticket.service;

import com.thomas.airline.booking.entity.Booking;
import com.thomas.airline.booking.repository.BookingRepository;
import com.thomas.airline.common.enums.TicketStatus;
import com.thomas.airline.exception.*;
import com.thomas.airline.flightseat.entity.FlightSeat;
import com.thomas.airline.flightseat.repository.FlightSeatRepository;
import com.thomas.airline.passenger.entity.Passenger;
import com.thomas.airline.passenger.repository.PassengerRepository;
import com.thomas.airline.ticket.dto.TicketRequestDto;
import com.thomas.airline.ticket.dto.TicketResponseDto;
import com.thomas.airline.ticket.entity.Ticket;
import com.thomas.airline.ticket.mapper.TicketMapper;
import com.thomas.airline.ticket.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;
    private  final PassengerRepository passengerRepository;
    private final FlightSeatRepository flightSeatRepository;
    private final TicketMapper ticketMapper;

    public TicketService(TicketRepository ticketRepository, BookingRepository bookingRepository, PassengerRepository passengerRepository, FlightSeatRepository flightSeatRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
        this.flightSeatRepository = flightSeatRepository;
        this.ticketMapper = ticketMapper;
    }
    public String generateUniqueTicketNumber(){
        String ticketNumber;
        do{
             ticketNumber= UUID.randomUUID().toString().replace("-","").substring(0,7).toUpperCase();
        }while(ticketRepository.existsByTicketNumber(ticketNumber));
        return ticketNumber;
    }
    public TicketResponseDto createTicket(TicketRequestDto requestDto){
        Booking booking=bookingRepository.findById(requestDto.getBookingId()).orElseThrow(()->new BookingNotFoundException("Booking is not available."));
        Passenger passenger=passengerRepository.findById(requestDto.getPassengerId()).orElseThrow(()-> new PassengerNotFoundException("Passenger is not available."));
        FlightSeat flightSeat=flightSeatRepository.findById(requestDto.getFlightSeatId()).orElseThrow(()-> new FlightSeatNotFoundException("Flight seat is not available."));
        if(ticketRepository.existsByPassenger(passenger)){
            throw new TicketAlreadyExistException("Ticket is already booked.");
        }
        if(ticketRepository.existsByFlightSeat(flightSeat)){
            throw new TicketAlreadyExistException("Ticket is already Booked.");
        }
        Ticket ticket=ticketMapper.requestDtoToTicket(requestDto);
        ticket.setTicketNumber(generateUniqueTicketNumber());
        ticket.setStatus(TicketStatus.BOOKED);
        ticket.setBooking(booking);
        ticket.setPassenger(passenger);
        ticket.setFlightSeat(flightSeat);
        Ticket savedTicket= ticketRepository.save(ticket);
        TicketResponseDto responseDto=ticketMapper.ticketToResponseDto(savedTicket);
        return responseDto;
    }
    public List<TicketResponseDto> getAllTickets(){
        List<Ticket> tickets=ticketRepository.findAll();
        List<TicketResponseDto> responseDtos=new ArrayList<>();
        for(Ticket ticket: tickets){
            TicketResponseDto responseDto=ticketMapper.ticketToResponseDto(ticket);
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
    public TicketResponseDto getTicketById(Long id){
        Ticket ticket= ticketRepository.findById(id).orElseThrow(()-> new TicketNotFoundException("Ticket is not available."));
        TicketResponseDto responseDto=ticketMapper.ticketToResponseDto(ticket);
        return responseDto;
    }
    public TicketResponseDto updateTicket(Long id, TicketRequestDto requestDto){
        Ticket ticket=ticketRepository.findById(id).orElseThrow(()-> new TicketNotFoundException("Ticket is not available."));
        Booking booking=bookingRepository.findById(requestDto.getBookingId()).orElseThrow(()-> new BookingNotFoundException("Booking is not available."));
        Passenger passenger=passengerRepository.findById(requestDto.getPassengerId()).orElseThrow(()-> new PassengerNotFoundException("Passenger is not available."));
        FlightSeat flightSeat=flightSeatRepository.findById(requestDto.getFlightSeatId()).orElseThrow(()-> new FlightSeatNotFoundException("Flight seat is not available."));
        Optional<Ticket> existPassenger=ticketRepository.findByPassenger(passenger);
        if(existPassenger.isPresent() && !existPassenger.get().getId().equals(id)){
            throw new TicketAlreadyExistException("Ticket is already available.");
        }
        Optional<Ticket> existFlightSeat=ticketRepository.findByFlightSeat(flightSeat);
        if(existFlightSeat.isPresent() && !existFlightSeat.get().getId().equals(id)){
            throw new TicketAlreadyExistException("Ticket is already available.");
        }
        ticket.setBooking(booking);
        ticket.setPassenger(passenger);
        ticket.setFlightSeat(flightSeat);
        Ticket savedTicket= ticketRepository.save(ticket);
        TicketResponseDto responseDto=ticketMapper.ticketToResponseDto(savedTicket);
        return responseDto;
    }
    public void deleteTicket(Long id){
        Ticket ticket=ticketRepository.findById(id).orElseThrow(()->  new TicketNotFoundException("Ticket is not available."));
        ticketRepository.delete(ticket);
    }
    public TicketResponseDto updateTicketStatus(Long ticketId,TicketStatus ticketStatus){
        Ticket ticket=ticketRepository.findById(ticketId).orElseThrow(()-> new TicketNotFoundException("Ticket is not available."));
        ticket.setStatus(ticketStatus);
        Ticket savedTicket=ticketRepository.save(ticket);
        TicketResponseDto ticketResponseDto=ticketMapper.ticketToResponseDto(savedTicket);
        return ticketResponseDto;
    }
    public TicketResponseDto getTicketByBookingId(Long bookingId) {
        Ticket ticket = ticketRepository.findByBookingId(bookingId).orElseThrow(() -> new TicketNotFoundException("Ticket not found."));
        return ticketMapper.ticketToResponseDto(ticket);
    }
}


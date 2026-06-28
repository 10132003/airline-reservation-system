package com.thomas.airline.booking.service;

import com.thomas.airline.booking.dto.BookingRequestDto;
import com.thomas.airline.booking.dto.BookingResponseDto;
import com.thomas.airline.booking.entity.Booking;
import com.thomas.airline.booking.mapper.BookingMapper;
import com.thomas.airline.booking.repository.BookingRepository;
import com.thomas.airline.common.enums.BookingStatus;
import com.thomas.airline.exception.BookingNotFoundException;
import com.thomas.airline.exception.FlightNotFoundException;
import com.thomas.airline.exception.TicketNotFoundException;
import com.thomas.airline.exception.UserNotFoundException;
import com.thomas.airline.flight.entity.Flight;
import com.thomas.airline.flight.repository.FlightRepository;
import com.thomas.airline.ticket.dto.TicketResponseDto;
import com.thomas.airline.ticket.entity.Ticket;
import com.thomas.airline.user.entity.User;
import com.thomas.airline.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper, UserRepository userRepository, FlightRepository flightRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
    }
    private String generateUniquePnr(){
        String pnr;
        do {
            pnr = "PNR" + UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 6)
                    .toUpperCase();
        }while(bookingRepository.existsByPnr(pnr));
        return pnr;
        }
    public BookingResponseDto createBooking(BookingRequestDto requestDto){
        User user=userRepository.findById(requestDto.getUserId()).orElseThrow(()->new UserNotFoundException("User is not available."));
        Flight flight=flightRepository.findById(requestDto.getFlightId()).orElseThrow(()-> new FlightNotFoundException("Flight is not available."));
        Booking booking=bookingMapper.requestDtoToBooking(requestDto);
        booking.setUser(user);
        booking.setFlight(flight);
        booking.setPnr(generateUniquePnr());
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus(BookingStatus.PENDING);

        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapper.bookingToResponseDto(savedBooking);
    }
    public List<BookingResponseDto> getAllBookings(){
        List<Booking> bookings=bookingRepository.findAll();
        List<BookingResponseDto> responseDtos=new ArrayList<>();
        for(Booking booking:bookings){
            BookingResponseDto responseDto=bookingMapper.bookingToResponseDto(booking);
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
    public BookingResponseDto getBookingById(Long id){
        Booking booking=bookingRepository.findById(id).orElseThrow(()-> new BookingNotFoundException("Booking is not available."));
        BookingResponseDto responseDto=bookingMapper.bookingToResponseDto(booking);
        return responseDto;
    }
    public BookingResponseDto updateBooking(Long id, BookingRequestDto requestDto){
        Booking booking=bookingRepository.findById(id).orElseThrow(()-> new BookingNotFoundException("Booking is not available."));
        User user=userRepository.findById(requestDto.getUserId()).orElseThrow(()->new UserNotFoundException("User is not available."));
        Flight flight=flightRepository.findById(requestDto.getFlightId()).orElseThrow(()-> new FlightNotFoundException("Flight is not available."));
        booking.setUser(user);
        booking.setFlight(flight);
        Booking savedBooking=bookingRepository.save(booking);
        BookingResponseDto responseDto=bookingMapper.bookingToResponseDto(savedBooking);
        return responseDto;
    }
    public void deleteBooking(Long id){
        Booking booking=bookingRepository.findById(id).orElseThrow(()-> new BookingNotFoundException("Booking is not available."));
        bookingRepository.delete(booking);
    }
    public BookingResponseDto updateBookingStatus(Long id,BookingStatus bookingStatus){
        Booking booking=bookingRepository.findById(id).orElseThrow(()-> new BookingNotFoundException("Booking is not available."));
        booking.setStatus(bookingStatus);
        Booking savedBooking=bookingRepository.save(booking);
        BookingResponseDto responseDto=bookingMapper.bookingToResponseDto(savedBooking);
        return responseDto;
    }

}

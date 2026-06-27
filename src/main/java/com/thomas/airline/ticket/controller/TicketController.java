package com.thomas.airline.ticket.controller;

import com.thomas.airline.ticket.dto.TicketRequestDto;
import com.thomas.airline.ticket.dto.TicketResponseDto;
import com.thomas.airline.ticket.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    @PostMapping
    public ResponseEntity<TicketResponseDto> createTicket(@Valid @RequestBody TicketRequestDto requestDto){
        TicketResponseDto responseDto=ticketService.createTicket(requestDto);
        return ResponseEntity.status(201).body(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<TicketResponseDto>> getAllTickets(){
        List<TicketResponseDto> responseDtos=ticketService.getAllTickets();
        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable Long id){
        TicketResponseDto responseDto=ticketService.getTicketById(id);
        return ResponseEntity.ok(responseDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDto> updateTicket(@PathVariable Long id, @Valid @RequestBody TicketRequestDto requestDto){
        TicketResponseDto responseDto=ticketService.updateTicket(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String > deleteTicket(@PathVariable Long id){
        ticketService.deleteTicket(id);
        return ResponseEntity.ok("Ticket is deleted successfully.");
    }
}

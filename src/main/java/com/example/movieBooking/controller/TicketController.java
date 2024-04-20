package com.example.movieBooking.controller;

import com.example.movieBooking.model.Ticket;
import com.example.movieBooking.model.TicketResponseDTO;
import com.example.movieBooking.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:4200")
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/book")
    public Ticket bookTicket(@RequestBody Ticket ticket) {
        return ticketService.bookTicket(ticket);
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/movie/{movieId}")
    public List<Ticket> getTicketsForMovie(@PathVariable Long movieId) {
        return ticketService.getTicketsForMovie(movieId);
    }
    @GetMapping("/{id}")
    public TicketResponseDTO getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok("ticket deleted");
    }
}


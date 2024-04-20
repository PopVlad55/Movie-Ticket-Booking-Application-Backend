package com.example.movieBooking.service;

import com.example.movieBooking.exceptions.MovieNotFoundException;
import com.example.movieBooking.exceptions.SeatAlreadyBookedException;
import com.example.movieBooking.model.Movie;
import com.example.movieBooking.model.Ticket;
import com.example.movieBooking.model.TicketResponseDTO;
import com.example.movieBooking.repository.MovieRepository;
import com.example.movieBooking.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final MovieRepository movieRepository;

    public Ticket bookTicket(Ticket ticket) {
        String seatNumber = ticket.getSeatNumber();
        if (seatNumber == null || seatNumber.isEmpty()) {
            throw new IllegalArgumentException("Seat number cannot be empty");
        }
        int seatNumberInt = Integer.parseInt(seatNumber);
        if (seatNumberInt < 0) {
            throw new IllegalArgumentException("Seat number cannot be negative");
        }
        if (seatNumberInt >40 ) {
            throw new IllegalArgumentException("Seat number cannot over 40");
        }

        if(isSeatAlreadyBooked(ticket.getSeatNumber())){
            throw new SeatAlreadyBookedException("Seat already booked");
        }
        if (ticket.getCustomerName() == null || ticket.getCustomerName().isEmpty()){
            throw new IllegalArgumentException("Name required");
        }

        Long movieId = ticket.getMovie().getId();
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        ticket.setMovie(movie);

        return ticketRepository.save(ticket);
    }


    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    public List<Ticket> getTicketsForMovie(Long movieId) {
        return ticketRepository.findByMovieId(movieId);
    }
    private boolean isSeatAlreadyBooked(String seatNumber) {
        return ticketRepository.existsBySeatNumber(seatNumber);
    }
    public TicketResponseDTO getTicketById(Long id) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
            ticketResponseDTO.setMovie(ticket.getMovie());
            ticketResponseDTO.setSeatNumber(ticket.getSeatNumber());
            ticketResponseDTO.setPrice(ticket.getPrice());
            ticketResponseDTO.setCustomerName(ticket.getCustomerName());
            return ticketResponseDTO;
        }
        return null;
    }
}
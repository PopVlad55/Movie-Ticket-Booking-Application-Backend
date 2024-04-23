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
        validateTicket(ticket);

        Movie movie = getMovieOrThrowNotFound(ticket.getMovie().getId());

        ticket.setMovie(movie);
        return ticketRepository.save(ticket);
    }

    private void validateTicket(Ticket ticket) {
        validateSeatNumber(ticket.getSeatNumber());
        validateCustomerName(ticket.getCustomerName());
        if (isSeatAlreadyBooked(ticket.getSeatNumber())) {
            throw new SeatAlreadyBookedException("Seat already booked");
        }
    }

    private void validateSeatNumber(String seatNumber) {
        if (seatNumber == null || seatNumber.isEmpty() || Integer.parseInt(seatNumber) < 0 || Integer.parseInt(seatNumber) > 40) {
            throw new IllegalArgumentException("Invalid seat number");
        }
    }

    private void validateCustomerName(String customerName) {
        if (customerName == null || customerName.isEmpty()) {
            throw new IllegalArgumentException("Name required");
        }
    }

    private Movie getMovieOrThrowNotFound(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));
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
        return optionalTicket.map(ticket -> {
            TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
            ticketResponseDTO.setMovie(ticket.getMovie());
            ticketResponseDTO.setSeatNumber(ticket.getSeatNumber());
            ticketResponseDTO.setPrice(ticket.getPrice());
            ticketResponseDTO.setCustomerName(ticket.getCustomerName());
            return ticketResponseDTO;
        }).orElse(null);
    }
}
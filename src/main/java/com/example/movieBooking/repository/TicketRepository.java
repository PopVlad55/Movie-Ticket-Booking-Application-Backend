package com.example.movieBooking.repository;

import com.example.movieBooking.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsBySeatNumber(String seatNumber);

    Optional<Ticket> findBySeatNumber(String seatNumber);
    List<Ticket> findByMovieId(Long movieId);
}
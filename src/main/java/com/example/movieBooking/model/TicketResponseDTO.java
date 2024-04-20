package com.example.movieBooking.model;
import lombok.Data;

@Data
public class TicketResponseDTO {
    private Movie movie;
    private String seatNumber;
    private double price;
    private String customerName;
}


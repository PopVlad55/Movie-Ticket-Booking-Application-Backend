package com.example.movieBooking;

import com.example.movieBooking.model.MovieFileReader;
import com.example.movieBooking.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandRunner implements CommandLineRunner {
    private final MovieRepository movieRepository;
    private final MovieFileReader movieFileReader;

    @Override
    public void run(String... args) {
        movieRepository.saveAll(movieFileReader.populateWithData());
    }
}
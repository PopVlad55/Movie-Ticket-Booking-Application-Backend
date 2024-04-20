package com.example.movieBooking.service;

import com.example.movieBooking.exceptions.ResourceNotFoundException;
import com.example.movieBooking.model.Movie;
import com.example.movieBooking.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
    }

    public Movie addMovie(Movie movie) {
        if (movie == null || movie.getTitle() == null || movie.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Movie cannot be empty");
        }
        if (movie.getDurationMinutes() < 1) {
            throw new IllegalArgumentException("Movie duration must be at least 1 minute");
        }
        if (movieRepository.findByTitle(movie.getTitle()).isPresent()) {
            throw new IllegalArgumentException("Movie already exists");
        }
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movie) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movie not found");
        }
        movie.setId(id);
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movie not found");
        }
        movieRepository.deleteById(id);
    }
}

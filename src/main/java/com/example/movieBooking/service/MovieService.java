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
        validateMovie(movie);
        checkIfMovieExists(movie.getTitle());

        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movie) {
        Movie existingMovie = getMovieById(id);
        movie.setId(existingMovie.getId());
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        getMovieById(id);
        movieRepository.deleteById(id);
    }

    private void validateMovie(Movie movie) {
        if (movie == null || movie.getTitle() == null || movie.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Movie title cannot be empty");
        }
        if (movie.getDurationMinutes() < 1) {
            throw new IllegalArgumentException("Movie duration must be at least 1 minute");
        }
    }

    private void checkIfMovieExists(String title) {
        movieRepository.findByTitle(title)
                .ifPresent(m -> {
                    throw new IllegalArgumentException("Movie with title '" + title + "' already exists");
                });
    }
}

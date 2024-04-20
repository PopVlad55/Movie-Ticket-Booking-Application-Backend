package com.example.movieBooking.model;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieFileReader implements DataProvider {

    @Override
    public List<Movie> populateWithData() {
        List<Movie> movies = new ArrayList<>();
        try {
            Resource resource = new ClassPathResource("movies.txt");
            File file = resource.getFile();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                movies.add(parseMovie(line));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    private Movie parseMovie(String line) {
        String[] movieParts = line.split("\\|");
        return Movie.builder()
                .title(movieParts[0])
                .director(movieParts[1])
                .durationMinutes(Integer.parseInt(movieParts[2]))
                .genre(movieParts[3])
                .build();
    }
}

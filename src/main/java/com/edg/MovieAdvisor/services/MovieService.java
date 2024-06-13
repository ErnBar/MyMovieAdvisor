package com.edg.MovieAdvisor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edg.MovieAdvisor.models.Actor;
import com.edg.MovieAdvisor.models.Movie;
import com.edg.MovieAdvisor.repositories.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie findById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie findByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteById(Long id) {
        Movie movie = findById(id);
        if (movie != null) {
            for (Actor actor : movie.getActors()) {
                actor.getMovies().remove(movie);
            }
            movie.setDirector(null);
            movieRepository.deleteById(id);
        }
    }
}

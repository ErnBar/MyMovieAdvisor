package com.edg.MovieAdvisor.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edg.MovieAdvisor.models.Director;
import com.edg.MovieAdvisor.models.Movie;
import com.edg.MovieAdvisor.repositories.DirectorRepository;
import com.edg.MovieAdvisor.repositories.MovieRepository;

@Service
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private MovieRepository movieRepository;

    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    public Director findById(Long id) {
        return directorRepository.findById(id).orElse(null);
    }

    public Director save(Director director) {
        return directorRepository.save(director);
    }

    public void deleteById(Long id) {
        Director director = findById(id);
        if (director != null) {
            for (Movie movie : director.getMovies()) {
                movie.setDirector(null);
                movieRepository.save(movie);
            }
            directorRepository.deleteById(id);
        }
    }

    public Director findByName(String name) {
        return directorRepository.findByName(name);
    }

    public List<Director> findByNameStartingWith(String prefix) {
        return directorRepository.findByNameStartingWith(prefix);
    }
}
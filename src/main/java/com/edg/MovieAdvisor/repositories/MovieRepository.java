package com.edg.MovieAdvisor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edg.MovieAdvisor.models.Movie;
import com.edg.MovieAdvisor.models.MovieDTO;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);
    
    @Query("SELECT new com.edg.MovieAdvisor.models.MovieDTO(m.title, AVG(r.score)) " +
           "FROM Movie m LEFT JOIN m.reviews r GROUP BY m.id " +
           "ORDER BY AVG(r.score) DESC")
    List<MovieDTO> findAllMoviesOrderByAverageScoreDesc();

    List<Movie> findByTitleStartingWith(String prefix);
}

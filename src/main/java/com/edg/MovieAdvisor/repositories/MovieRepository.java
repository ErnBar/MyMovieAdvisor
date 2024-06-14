package com.edg.MovieAdvisor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edg.MovieAdvisor.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);
    
    @Query(value = "SELECT m.title,AVG(r.score) AS score  FROM movies m LEFT JOIN reviews r ON m.id = r.movie_id GROUP BY m.id ORDER BY AVG(r.score) DESC limit 10", nativeQuery = true)
    List<Movie> findAllMoviesOrderByAverageScoreDesc();

    List<Movie> findByTitleStartingWith(String prefix);
}

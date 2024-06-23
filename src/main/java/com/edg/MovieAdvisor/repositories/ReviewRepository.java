package com.edg.MovieAdvisor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edg.MovieAdvisor.models.Movie;
import com.edg.MovieAdvisor.models.Review;
import com.edg.MovieAdvisor.models.User;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT AVG(r.score) FROM Review r WHERE r.movie.id = :movieId")
    Double findAverageScoreByMovieId(@Param("movieId") Long movieId);

    boolean existsByMovieAndOp(Movie movie, User op);
    
}

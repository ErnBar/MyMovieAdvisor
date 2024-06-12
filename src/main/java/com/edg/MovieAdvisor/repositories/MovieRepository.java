package com.edg.MovieAdvisor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edg.MovieAdvisor.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);
}

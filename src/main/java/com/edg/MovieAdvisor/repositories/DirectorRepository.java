package com.edg.MovieAdvisor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edg.MovieAdvisor.models.Director;

public interface DirectorRepository extends JpaRepository<Director, Long> {
    Director findByName(String name);
}

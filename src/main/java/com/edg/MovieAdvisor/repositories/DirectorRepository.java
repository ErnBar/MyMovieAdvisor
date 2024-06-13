package com.edg.MovieAdvisor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edg.MovieAdvisor.models.Director;

public interface DirectorRepository extends JpaRepository<Director, Long> {
    Director findByName(String name);
    List<Director> findByNameStartingWith(String prefix);
}

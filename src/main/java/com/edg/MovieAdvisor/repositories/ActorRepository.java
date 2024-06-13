package com.edg.MovieAdvisor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edg.MovieAdvisor.models.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    Actor findByName(String name);
    List<Actor> findByNameStartingWith(String prefix);
}

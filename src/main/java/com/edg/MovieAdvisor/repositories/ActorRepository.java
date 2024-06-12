package com.edg.MovieAdvisor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edg.MovieAdvisor.models.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    Actor findByName(String name);
}

package com.edg.MovieAdvisor.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edg.MovieAdvisor.models.Actor;
import com.edg.MovieAdvisor.models.Movie;
import com.edg.MovieAdvisor.repositories.ActorRepository;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public List<Actor> findAll() {
        return actorRepository.findAll();
    }

    public Actor findById(Long id) {
        return actorRepository.findById(id).orElse(null);
    }

    public Actor save(Actor actor) {
        return actorRepository.save(actor);
    }

    public void deleteById(Long id) {
        Actor actor = findById(id);
        if (actor != null) {
            for (Movie movie : actor.getMovies()) {
                movie.getActors().remove(actor);
            }
            actorRepository.deleteById(id);
        }
    }

    public Actor findByName(String name) {
        return actorRepository.findByName(name);
    }

    public List<Actor> findByNameStartingWith(String prefix) {
        return actorRepository.findByNameStartingWith(prefix);
    }
}
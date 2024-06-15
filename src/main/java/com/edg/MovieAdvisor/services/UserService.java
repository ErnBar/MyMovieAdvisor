package com.edg.MovieAdvisor.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edg.MovieAdvisor.models.Movie;
import com.edg.MovieAdvisor.models.User;
import com.edg.MovieAdvisor.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        User user=findById(id);
        if (user != null) {
            for(Movie movie : user.getFavoriteMovies()){
                movie.getFavoriteUserMovies().remove(user);
            }
             userRepository.deleteById(id);
        }
    }

    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByDisplayname(String displayname) {
        return userRepository.findByDisplayname(displayname);
    }
}
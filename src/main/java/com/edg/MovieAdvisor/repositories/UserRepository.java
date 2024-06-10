package com.edg.MovieAdvisor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edg.MovieAdvisor.models.User;



public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User findByDisplayname(String displayname);
}

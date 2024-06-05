package com.edg.MovieAdvisor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edg.MovieAdvisor.models.User;

public interface UserRepository extends JpaRepository<User,Long> {
    
}

package com.edg.MovieAdvisor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.edg.MovieAdvisor.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    
}

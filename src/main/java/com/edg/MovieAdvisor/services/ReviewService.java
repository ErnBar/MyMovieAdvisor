package com.edg.MovieAdvisor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edg.MovieAdvisor.models.Movie;
import com.edg.MovieAdvisor.models.Review;
import com.edg.MovieAdvisor.models.User;
import com.edg.MovieAdvisor.repositories.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    public boolean existsByMovieAndOp(Movie movie, User op) {
        return reviewRepository.existsByMovieAndOp(movie, op);
    }
}
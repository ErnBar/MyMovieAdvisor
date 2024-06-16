package com.edg.MovieAdvisor.models;



import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private int score;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date review_date;

    @ManyToOne
    @JoinColumn(name="op")
    @JsonManagedReference
    private User op;

    @ManyToOne
    @JoinColumn(name="movie_id")
    @JsonManagedReference
    private Movie movie;
    
}

package com.edg.MovieAdvisor.models;



import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="op")
    private User op;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="movie_id")
    private Movie movie;
    
}

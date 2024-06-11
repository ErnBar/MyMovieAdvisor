package com.edg.MovieAdvisor.models;

import java.sql.Date;
import java.util.List;

import org.hibernate.annotations.SQLJoinTableRestriction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date date;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] moviepicture;

    //@SQLJoinTableRestriction("role = 'DIRECTOR'")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "movie_worker",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "worker_id")
    )
    private List<Worker> workers;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}

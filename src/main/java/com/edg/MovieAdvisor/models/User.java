package com.edg.MovieAdvisor.models;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String username;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationdate;

    @Column(nullable = false)
    private String displayname;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] profilepicture;

    @OneToMany(mappedBy = "op", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;


    
}

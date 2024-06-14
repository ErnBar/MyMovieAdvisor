package com.edg.MovieAdvisor.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieDTO {
    
    private String title;

    private Double avgScore;
}

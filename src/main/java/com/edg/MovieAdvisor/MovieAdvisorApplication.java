package com.edg.MovieAdvisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.edg.MovieAdvisor.repositories")
public class MovieAdvisorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieAdvisorApplication.class, args);
	}

}
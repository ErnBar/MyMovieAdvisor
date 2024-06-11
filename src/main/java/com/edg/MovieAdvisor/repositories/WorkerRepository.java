package com.edg.MovieAdvisor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.edg.MovieAdvisor.models.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Worker findByName(String name);
}

package com.edg.MovieAdvisor.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edg.MovieAdvisor.models.Worker;
import com.edg.MovieAdvisor.repositories.WorkerRepository;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    public List<Worker> findAll() {
        return workerRepository.findAll();
    }

    public Worker findById(Long id) {
        return workerRepository.findById(id).orElse(null);
    }

    public Worker save(Worker worker) {
        return workerRepository.save(worker);
    }

    public void deleteById(Long id) {
        workerRepository.deleteById(id);
    }

    public Worker findByName(String name) {
        return workerRepository.findByName(name);
    }
}
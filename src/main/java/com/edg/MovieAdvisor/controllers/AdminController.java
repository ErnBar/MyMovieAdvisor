package com.edg.MovieAdvisor.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.edg.MovieAdvisor.models.User;
import com.edg.MovieAdvisor.models.Worker;
import com.edg.MovieAdvisor.services.UserService;
import com.edg.MovieAdvisor.services.WorkerService;

import jakarta.servlet.http.HttpSession;


@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private WorkerService workerService;

    @GetMapping("/usersList")
    public String usersList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "usersList";
    }

    

    @PostMapping("/deleteUser")
    public String deleteUser(@ModelAttribute("userId") Long userId) {
        userService.deleteById(userId);
        return "redirect:/usersList";
    }

    

    @GetMapping("/workersList")
    public String workersList(Model model, HttpSession session) {
        model.addAttribute("workers", workerService.findAll());
        model.addAttribute("worker", new Worker());
        return "workersList";
    }

    @PostMapping("/workeradd")
    public String addWorker(@ModelAttribute("worker") @Validated Worker worker, Model model) {
        workerService.save(worker);
        return "redirect:/workersList";
    }

    @PostMapping("/deleteWorker")
    public String deleteWorker(@ModelAttribute("workerId") Long workerId) {
        workerService.deleteById(workerId);
        return "redirect:/workersList";
    }

    
    
}

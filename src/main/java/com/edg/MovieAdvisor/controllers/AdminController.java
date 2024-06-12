package com.edg.MovieAdvisor.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.edg.MovieAdvisor.models.Actor;
import com.edg.MovieAdvisor.models.Director;
import com.edg.MovieAdvisor.models.Movie;
import com.edg.MovieAdvisor.models.User;
import com.edg.MovieAdvisor.services.ActorService;
import com.edg.MovieAdvisor.services.DirectorService;
import com.edg.MovieAdvisor.services.MovieService;
import com.edg.MovieAdvisor.services.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private MovieService movieService;
    

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



    @GetMapping("/actorsList")
    public String actorsList(Model model, HttpSession session) {
        model.addAttribute("actors", actorService.findAll());
        model.addAttribute("actor", new Actor());
        return "actorsList";
    }

    @PostMapping("/actoradd")
    public String addActor(@ModelAttribute("actor") @Validated Actor actor, Model model) {
        actorService.save(actor);
        return "redirect:/actorsList";
    }

    @PostMapping("/deleteActor")
    public String deleteActor(@ModelAttribute("actorId") Long actorId) {
        actorService.deleteById(actorId);
        return "redirect:/actorsList";
    }


    
    @GetMapping("/directorsList")
    public String directorsList(Model model, HttpSession session) {
        model.addAttribute("directors", directorService.findAll());
        model.addAttribute("director", new Director());
        return "directorsList";
    }

    @PostMapping("/directorAdd")
    public String addDirector(@ModelAttribute("director") @Validated Director director, Model model) {
        directorService.save(director);
        return "redirect:/directorsList";
    }

    @PostMapping("/deleteDirector")
    public String deleteDirector(@ModelAttribute("directorId") Long directorId) {
        directorService.deleteById(directorId);
        return "redirect:/directorsList";
    }

    

    @GetMapping("/moviesList")
    public String moviesList(Model model, HttpSession session) {
        boolean isAdmin=true;
        Object utente = session.getAttribute("user");
            if (utente!=null && utente instanceof User) {
                User u = (User) utente;
                if (!u.getUsername().equalsIgnoreCase("admin")) {
                    isAdmin = false;
                }
            }
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("movies", movieService.findAll());
        model.addAttribute("movie", new Movie());
        return "moviesList";
    }

    @PostMapping("/movieAdd")
    public String addMovie(@ModelAttribute("movie") @Validated Movie movie, Model model) {
        movieService.save(movie);
        return "redirect:/moviesList";
    }

    @PostMapping("/deleteMovie")
    public String deleteMovie(@ModelAttribute("movieId") Long movieId) {
        movieService.deleteById(movieId);
        return "redirect:/moviesList";
    }
}

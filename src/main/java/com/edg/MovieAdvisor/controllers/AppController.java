package com.edg.MovieAdvisor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edg.MovieAdvisor.models.User;
import com.edg.MovieAdvisor.services.MovieService;
import com.edg.MovieAdvisor.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AppController {

    
    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;


    @GetMapping("/")
    public String home(HttpSession session,Model model){
        String actualUser="";
        boolean isAdmin=false;
        if (session.getAttribute("logged")==null) {
            return "redirect:/formLogin";
        }else {
            Object utente = session.getAttribute("user");
            if (utente!=null && utente instanceof User) {
                User u = (User) utente;
                if (!u.getUsername().equalsIgnoreCase("admin")) {
                    actualUser += u.getDisplayname();
                }
                else {
                    isAdmin=true;
                }
                
            }
            model.addAttribute("topMovie",movieService.findAllMoviesOrderByAverageScoreDesc());
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("actualUser", actualUser);
            return "main.html";
        }
    }

    @GetMapping("/formLogin")
    public String login(){
        return "formLogin.html";
    }

    

    @PostMapping("/login")
    public String login(
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        Model model,
        HttpSession session){
            User loggedUser= userService.findByUsernameAndPassword(username, password);
            if (loggedUser==null) {
                model.addAttribute("error", "Username or password doesn't exist.");
                return "formLogin.html";
            }
            else{
                session.setAttribute("logged", "ok");
                session.setAttribute("user", loggedUser);
                return "redirect:/";
                }
        }
    
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.setAttribute("logged", null);
        session.setAttribute("user", null);
        return "redirect:/";
    }

    @GetMapping("/error")
    public String error(){
        return "error.html";
    }
    
}

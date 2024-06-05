package com.edg.MovieAdvisor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.edg.MovieAdvisor.models.User;
import com.edg.MovieAdvisor.services.UserService;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register.html";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        userService.save(user);
        model.addAttribute("registrationMessage", "Registrazione effettuata!");
        return "confermaregistrazione.html";
    }
}
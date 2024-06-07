package com.edg.MovieAdvisor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/registeruser")
    public String registerUser(@ModelAttribute("user") @Validated User user, Model model) {
        if (userService.findByUsername(user.getUsername()).isPresent() || userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email or username already exists");
            return "register.html";
        }
        userService.save(user);
        return "confermaregistrazione.html";
    }
}
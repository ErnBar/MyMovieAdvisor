package com.edg.MovieAdvisor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.edg.MovieAdvisor.models.User;
import com.edg.MovieAdvisor.services.UserService;

import jakarta.servlet.http.HttpSession;

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

    @GetMapping("/userPanel")
    public String userPanel(HttpSession session, Model model) {
        if (session.getAttribute("logged") == null) {
            return "redirect:/formLogin";
        }
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "userPanel.html";
    }

    @PostMapping("/updateAccount")
    public String updateAccount(@ModelAttribute("user") User updatedUser, HttpSession session) {
        User originalUser = (User) session.getAttribute("user");
        if (updatedUser.getUsername() != null) {
            originalUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getDisplayname() != null) {
            originalUser.setDisplayname(updatedUser.getDisplayname());
        }
        if (updatedUser.getEmail() != null) {
            originalUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            originalUser.setPassword(updatedUser.getPassword());
        }
        userService.save(originalUser);
        session.setAttribute("user", originalUser);
        return "redirect:/userPanel";
    }

    @PostMapping("/deleteAccount")
    public String deleteAccount(HttpSession session) {
        User user = (User) session.getAttribute("user");
        userService.deleteById(user.getId());
        session.removeAttribute("user");
        session.removeAttribute("logged");
        return "formLogin.html";
    }
}
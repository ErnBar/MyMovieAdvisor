package com.edg.MovieAdvisor.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.edg.MovieAdvisor.services.UserService;


@Controller
public class AdminController {

    @Autowired
    private UserService userService;

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

    
    
}

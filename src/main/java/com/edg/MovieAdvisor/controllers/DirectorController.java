package com.edg.MovieAdvisor.controllers;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.edg.MovieAdvisor.models.Director;
import com.edg.MovieAdvisor.models.User;
import com.edg.MovieAdvisor.services.DirectorService;
import com.edg.MovieAdvisor.services.MovieService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DirectorController {
    @Autowired
    private DirectorService directorService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/directorDetail")
    public String directorPanel(@RequestParam("name") String Name, HttpSession session, Model model) {
        if (session.getAttribute("logged") == null) {
            return "redirect:/formLogin";
        }
        User loggedUser = (User)session.getAttribute("user");
        
        
        Director director = directorService.findByName(Name);
        if (director==null) {
            return "redirect:/error";
        }
        boolean admin=false;
        if (loggedUser.getUsername().equalsIgnoreCase("admin")) {
            admin=true;
        }
        String pfp = "";
        if (director.getProfilepicture() != null) {
            pfp = Base64.getEncoder().encodeToString(director.getProfilepicture());
        }
        model.addAttribute("director", director);
        model.addAttribute("admin", admin);
        model.addAttribute("pfp", pfp);
        model.addAttribute("movies", director.getMovies());
        return "directorDetail.html";
    }

    @PostMapping("/uploadDirectorPicture")
    public String uploadProfilePicture(@RequestParam("directorId") Long directorId, @RequestParam("profilePicture") MultipartFile file, HttpSession session) {
        if (session.getAttribute("logged") == null) {
            return "redirect:/formLogin";
        }
        if (!file.isEmpty() && (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png"))) {
            try {
                Director director = directorService.findById(directorId);
                if (director != null) {
                    director.setProfilepicture(file.getBytes());
                    directorService.save(director);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/directorDetail?name=" + directorService.findById(directorId).getName();
    }

    @PostMapping("/directorUpdate")
    public String updateDirector(@RequestParam("id") Long id, @RequestParam("name") String name,
                            @RequestParam("birthday") String birthday, @RequestParam("nationality") String nationality) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(birthday, formatter);
        Date date = Date.valueOf(localDate);
        Director director = directorService.findById(id);
        if (director != null) {
            director.setName(name);
            director.setBirthday(date); 
            director.setNationality(nationality);
            directorService.save(director);
        }
        return "redirect:/directorDetail?name=" + name;
    }

}

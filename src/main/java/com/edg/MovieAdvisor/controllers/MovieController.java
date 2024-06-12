package com.edg.MovieAdvisor.controllers;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.edg.MovieAdvisor.models.Director;
import com.edg.MovieAdvisor.models.Movie;
import com.edg.MovieAdvisor.models.User;
import com.edg.MovieAdvisor.services.DirectorService;
import com.edg.MovieAdvisor.services.MovieService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private DirectorService directorService;



    @GetMapping("/movieDetail")
    public String moviePanel(@RequestParam("title") String title, HttpSession session, Model model) {
        if (session.getAttribute("logged") == null) {
            return "redirect:/formLogin";
        }
        User loggedUser = (User)session.getAttribute("user");
        
        Movie movie = movieService.findByTitle(title);
        if (movie==null) {
            return "redirect:/error";
        }
        boolean admin=false;
        if (loggedUser.getUsername().equalsIgnoreCase("admin")) {
            admin=true;
        }
        String moviepic = "";
        if (movie.getMoviepicture() != null) {
            moviepic = Base64.getEncoder().encodeToString(movie.getMoviepicture());
        }
        List<Director> directors = directorService.findAll();
        model.addAttribute("movie", movie);
        model.addAttribute("admin", admin);
        model.addAttribute("moviepic", moviepic);
        model.addAttribute("director", movie.getDirector());
        model.addAttribute("directors", directors);
        return "movieDetail.html";
    }

    @PostMapping("/uploadMoviePicture")
    public String uploadMoviePicture(@RequestParam("movieId") Long movieId, @RequestParam("moviePicture") MultipartFile file, HttpSession session) {
        if (session.getAttribute("logged") == null) {
            return "redirect:/formLogin";
        }
        if (!file.isEmpty() && (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png"))) {
            try {
                Movie movie = movieService.findById(movieId);
                if (movie != null) {
                    movie.setMoviepicture(file.getBytes());
                    movieService.save(movie);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/movieDetail?title=" + movieService.findById(movieId).getTitle();
    }

    @PostMapping("/movieUpdate")
    public String updateMovie(@RequestParam("id") Long id, @RequestParam("title") String title,
                            @RequestParam("date") String date, @RequestParam("description") String description) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        Date realDate = Date.valueOf(localDate);
        Movie movie = movieService.findById(id);
        if (movie != null) {
            movie.setTitle(title);
            movie.setDate(realDate); 
            movie.setDescription(description);
            movieService.save(movie);
        }
        return "redirect:/movieDetail?title=" + title;
    }

    @PostMapping("/directorAssociation")
    public String directorAssociation(@RequestParam("movieId") Long movieId, @RequestParam("directorName") String directorName) {
        Movie movie = movieService.findById(movieId);
        if (movie == null) {
            return "redirect:/error";
        }
        Director director = directorService.findByName(directorName);
        if (director == null) {
            return "redirect:/error";
        }
        movie.setDirector(director);
        movieService.save(movie);
        return "redirect:/movieDetail?title=" + movie.getTitle();
}



    
    
}

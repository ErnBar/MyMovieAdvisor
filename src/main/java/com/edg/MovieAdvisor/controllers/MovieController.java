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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.edg.MovieAdvisor.models.Actor;
import com.edg.MovieAdvisor.models.Director;
import com.edg.MovieAdvisor.models.Movie;
import com.edg.MovieAdvisor.models.Review;
import com.edg.MovieAdvisor.models.User;
import com.edg.MovieAdvisor.services.ActorService;
import com.edg.MovieAdvisor.services.DirectorService;
import com.edg.MovieAdvisor.services.MovieService;
import com.edg.MovieAdvisor.services.ReviewService;
import com.edg.MovieAdvisor.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;



    @GetMapping("/movieDetail")
    public String moviePanel(@RequestParam("title") String title, HttpSession session, Model model) {
        if (session.getAttribute("logged") == null) {
            return "redirect:/formLogin";
        }
        User loggedUser = (User)session.getAttribute("user");
        String actualUser="";
        Movie movie = movieService.findByTitle(title);
        if (movie==null) {
            return "redirect:/error";
        }
        boolean admin=false;
        if (loggedUser.getUsername().equalsIgnoreCase("admin")) {
            admin=true;
        }
        if (!loggedUser.getUsername().equalsIgnoreCase("admin")) {
            actualUser += loggedUser.getDisplayname();
        }else {
            admin=true;
        }
        String moviepic = "";
        if (movie.getMoviepicture() != null) {
            moviepic = Base64.getEncoder().encodeToString(movie.getMoviepicture());
        }
        boolean review=false;
        if (reviewService.existsByMovieAndOp(movie, loggedUser)) {
            review=true;
        }
        List<Director> directors = directorService.findAll();
        List<Actor> actors = actorService.findAll();
        Double averageScore = movieService.getAverageScore(movie.getId());
        model.addAttribute("reviewed", review);
        model.addAttribute("actualUser", actualUser);
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("movie", movie);
        model.addAttribute("admin", admin);
        model.addAttribute("moviepic", moviepic);
        model.addAttribute("director", movie.getDirector());
        model.addAttribute("directors", directors);
        model.addAttribute("actors", movie.getActors());
        model.addAttribute("actorsList", actors);
        model.addAttribute("movieReview", movie.getReviews());
        model.addAttribute("averageScore", averageScore);
        model.addAttribute("userFvrt",movie.getFavoriteUserMovies());
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
                            @RequestParam("date") String date, @RequestParam("description") String description,
                            @RequestParam("trailer")String trailer) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        Date realDate = Date.valueOf(localDate);
        Movie movie = movieService.findById(id);
        if (movie != null) {
            movie.setTitle(title);
            movie.setDate(realDate); 
            movie.setDescription(description);
            movie.setTrailer(trailer);
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
    @PostMapping("/actorsAssociation")
    public String actorsAssociation(@RequestParam("movieId") Long movieId, @RequestParam("actorName") String actorName) {
        Movie movie = movieService.findById(movieId);
        if (movie == null) {
            return "redirect:/error";
        }
        Actor actor = actorService.findByName(actorName);
        if (actor == null) {
            return "redirect:/error";
        }
        if (!movie.getActors().contains(actor)) {
            movie.getActors().add(actor);
            actor.getMovies().add(movie); 
        }
        movieService.save(movie);
        actorService.save(actor); 
        return "redirect:/movieDetail?title=" + movie.getTitle();
    }

    @PostMapping("/favoriteAssociation")
    public String favoriteAssociation(@RequestParam("movieId") Long movieId, @RequestParam("userId") Long userId) {
        User user = userService.findById(userId);
        Movie movie = movieService.findById(movieId);
        
        if (!user.getFavoriteMovies().contains(movie)) {
            user.getFavoriteMovies().add(movie);
            movie.getFavoriteUserMovies().add(user);
            userService.save(user);
            movieService.save(movie);
        }
        return "redirect:/movieDetail?title=" + movie.getTitle();
    }



    @PostMapping("/deleteActorAssociation")
    public String deleteActorAssociation(@RequestParam("movieId") Long movieId, @RequestParam("actorId") Long actorId) {
        Movie movie = movieService.findById(movieId);
        if (movie == null) {
            return "redirect:/error";
        }
        Actor actor = actorService.findById(actorId);
        if (actor == null) {
            return "redirect:/error";
        }
        if (movie.getActors().remove(actor)) {
            actor.getMovies().remove(movie);
            movieService.save(movie);
            actorService.save(actor);
        }
        return "redirect:/movieDetail?title=" + movie.getTitle();
    }

    @GetMapping("/searchDirectors")
    @ResponseBody
    public List<Director> searchDirectors(@RequestParam("prefix") String prefix) {
        return directorService.findByNameStartingWith(prefix);
    }

    @GetMapping("/searchActors")
    @ResponseBody
    public List<Actor> searchActors(@RequestParam("prefix") String prefix) {
        return actorService.findByNameStartingWith(prefix);
    }

    @GetMapping("/searchMovies")
    @ResponseBody
    public List<Movie> searchMovies(@RequestParam("prefix") String prefix) {
        return movieService.findByTitleStartingWith(prefix);
    }

    @PostMapping("/addReview")
    public String addReview(@RequestParam("movieId") Long movieId,HttpSession session,@ModelAttribute("review") @Validated Review review, Model model) {
        Movie movie = movieService.findById(movieId);
        if (movie == null) {
            return "redirect:/error";
        }
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser == null) {
            return "redirect:/formLogin";
        }
        review.setMovie(movie);
        review.setOp(loggedUser);
        reviewService.save(review);
        return "redirect:/movieDetail?title=" + movie.getTitle();
    }

    @PostMapping("/deleteReview")
    public String deleteReview(@RequestParam("reviewId") Long reviewId, HttpSession session) {
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser == null) {
            return "redirect:/formLogin";
        }
        Review review = reviewService.findById(reviewId);
        if (review == null) {
            return "redirect:/error";
        }
        Movie movie = review.getMovie();
        reviewService.deleteById(reviewId);
        return "redirect:/movieDetail?title=" + movie.getTitle();
    }




    
    
}

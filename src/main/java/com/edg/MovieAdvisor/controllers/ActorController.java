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

import com.edg.MovieAdvisor.models.Actor;
import com.edg.MovieAdvisor.models.User;
import com.edg.MovieAdvisor.services.ActorService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ActorController {
    @Autowired
    private ActorService actorService;

    @GetMapping("/actorDetail")
    public String actorPanel(@RequestParam("name") String Name, HttpSession session, Model model) {
        if (session.getAttribute("logged") == null) {
            return "redirect:/formLogin";
        }
        User loggedUser = (User)session.getAttribute("user");
        
        
        Actor actor = actorService.findByName(Name);
        if (actor==null) {
            return "redirect:/error";
        }
        boolean admin=false;
        if (loggedUser.getUsername().equalsIgnoreCase("admin")) {
            admin=true;
        }
        String pfp = "";
        if (actor.getProfilepicture() != null) {
            pfp = Base64.getEncoder().encodeToString(actor.getProfilepicture());
        }
        model.addAttribute("actor", actor);
        model.addAttribute("admin", admin);
        model.addAttribute("pfp", pfp);
        return "actorDetail.html";
    }

    @PostMapping("/uploadActorPicture")
    public String uploadProfilePicture(@RequestParam("actorId") Long actorId, @RequestParam("profilePicture") MultipartFile file, HttpSession session) {
        if (session.getAttribute("logged") == null) {
            return "redirect:/formLogin";
        }
        if (!file.isEmpty() && (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png"))) {
            try {
                Actor actor = actorService.findById(actorId);
                if (actor != null) {
                    actor.setProfilepicture(file.getBytes());
                    actorService.save(actor);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/actorDetail?name=" + actorService.findById(actorId).getName();
    }

    @PostMapping("/actorUpdate")
    public String updateActor(@RequestParam("id") Long id, @RequestParam("name") String name,
                            @RequestParam("birthday") String birthday, @RequestParam("nationality") String nationality) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(birthday, formatter);
        Date date = Date.valueOf(localDate);
        Actor actor = actorService.findById(id);
        if (actor != null) {
            actor.setName(name);
            actor.setBirthday(date); 
            actor.setNationality(nationality);
            actorService.save(actor);
        }
        return "redirect:/actorDetail?name=" + name;
    }

}

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

import com.edg.MovieAdvisor.models.User;
import com.edg.MovieAdvisor.models.Worker;
import com.edg.MovieAdvisor.services.UserService;
import com.edg.MovieAdvisor.services.WorkerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class WorkerController {


    @Autowired
    private UserService userService;

    @Autowired
    private WorkerService workerService;

    @GetMapping("/workerDetail")
    public String workerPanel(@RequestParam("name") String Name, HttpSession session, Model model) {
        if (session.getAttribute("logged") == null) {
            return "redirect:/formLogin";
        }
        User loggedUser = (User)session.getAttribute("user");
        
        
        Worker worker = workerService.findByName(Name);
        if (worker==null) {
            return "redirect:/error";
        }
        boolean admin=false;
        if (loggedUser.getUsername().equalsIgnoreCase("admin")) {
            admin=true;
        }
        String pfp = "";
        if (worker.getProfilepicture() != null) {
            pfp = Base64.getEncoder().encodeToString(worker.getProfilepicture());
        }
        model.addAttribute("worker", worker);
        model.addAttribute("admin", admin);
        model.addAttribute("pfp", pfp);
        return "workerDetail.html";
    }

    @PostMapping("/uploadWorkerPicture")
    public String uploadProfilePicture(@RequestParam("workerId") Long workerId, @RequestParam("profilePicture") MultipartFile file, HttpSession session) {
        if (session.getAttribute("logged") == null) {
            return "redirect:/formLogin";
        }
        if (!file.isEmpty() && (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png"))) {
            try {
                Worker worker = workerService.findById(workerId);
                if (worker != null) {
                    worker.setProfilepicture(file.getBytes());
                    workerService.save(worker);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/workerDetail?name=" + workerService.findById(workerId).getName();
    }

    @PostMapping("/workerUpdate")
    public String updateWorker(@RequestParam("id") Long id, @RequestParam("name") String name,
                            @RequestParam("birthday") String birthday, @RequestParam("nationality") String nationality,
                            @RequestParam("role") String role) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(birthday, formatter);
        Date date = Date.valueOf(localDate);
        Worker worker = workerService.findById(id);
        if (worker != null) {
            worker.setName(name);
            worker.setBirthday(date); 
            worker.setNationality(nationality);
            worker.setRole(role);
            workerService.save(worker);
        }
        return "redirect:/workerDetail?name=" + name;
    }


    
    
}

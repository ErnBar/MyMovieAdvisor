package com.edg.MovieAdvisor.controllers;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    public String userPanel(@RequestParam("displayname") String displayName, HttpSession session, Model model) {
        if (session.getAttribute("logged") == null) {
            return "redirect:/formLogin";
        }
        User loggedUser = (User)session.getAttribute("user");
        boolean userlog=false;
        
        User user = userService.findByDisplayname(displayName);
        if (user==null) {
            return "redirect:/error";
        }
        if (loggedUser.getUsername().equalsIgnoreCase(user.getUsername())) {
            userlog=true;
            
        }
        boolean admin=false;
        if (loggedUser.getUsername().equalsIgnoreCase("admin")) {
            admin=true;
        }
        String pfp = "";
        if (user.getProfilepicture() != null) {
            pfp = Base64.getEncoder().encodeToString(user.getProfilepicture());
        }
        model.addAttribute("admin", admin);
        model.addAttribute("userlog", userlog);
        model.addAttribute("user", user);
        model.addAttribute("pfp", pfp);
        model.addAttribute("favorite", user.getFavoriteMovies());
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
        return "redirect:/userPanel?displayname="+originalUser.getDisplayname();
    }

    @PostMapping("/deleteAccount")
    public String deleteAccount(HttpSession session) {
        User user = (User) session.getAttribute("user");
        userService.deleteById(user.getId());
        session.removeAttribute("user");
        session.removeAttribute("logged");
        return "formLogin.html";
    }

    // @PostMapping("/uploadProfilePicture")
    // public String uploadProfilePicture(@RequestParam("profilePicture") MultipartFile file, HttpSession session) {
    //     User user = (User) session.getAttribute("user");
    //     if (!file.isEmpty() && file.getContentType().equals("image/jpeg")) {
    //         try {
    //             // Ottieni il percorso assoluto della directory del progetto
    //             String projectPath = System.getProperty("user.dir");
    //             // Definisci il percorso dove salvare l'immagine del profilo
    //             String uploadDir = projectPath + "/src/main/resources/static/user-photos/" + user.getId();
    //             Path uploadPath = Paths.get(uploadDir);

    //             // Crea la directory se non esiste
    //             if (!Files.exists(uploadPath)) {
    //                 Files.createDirectories(uploadPath);
    //             }

    //             String fileName = "profile.jpg";
    //             Path filePath = uploadPath.resolve(fileName);

    //             // Salva il file nel percorso definito
    //             file.transferTo(filePath.toFile());

    //         } catch (IOException e) {
    //             e.printStackTrace();
    //         }
    //     }
    //     return "redirect:/userPanel";
    // }//metodo senza base64

    @PostMapping("/uploadProfilePicture")
    public String uploadProfilePicture(@RequestParam("profilePicture") MultipartFile file, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (!file.isEmpty() && (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png"))) {
            try {
                user.setProfilepicture(file.getBytes());
                userService.save(user);
                
                // byte[] bytes = file.getBytes();
                // String base64Image = Base64.getEncoder().encodeToString(bytes);

                // user.setProfilepicturebase64(base64Image);
                // userService.save(user);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/userPanel?displayname="+user.getDisplayname();
    }
}
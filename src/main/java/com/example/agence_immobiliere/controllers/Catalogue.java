package com.example.agence_immobiliere.controllers;

import com.example.agence_immobiliere.domains.UserRole;
import com.example.agence_immobiliere.entities.Annonce;
import com.example.agence_immobiliere.entities.User;
import com.example.agence_immobiliere.repositories.AnnonceRepository;
import com.example.agence_immobiliere.services.AnnonceService;
import com.example.agence_immobiliere.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class Catalogue {

    @Autowired
    private AnnonceRepository annonceRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AnnonceService annonceService;



    @GetMapping("/acceuil")
    public ModelAndView viewacceuilPage(Model model) {
        List<Annonce> listAnnonces = annonceService.findAll();
        model.addAttribute("listAnnonces", listAnnonces);

        return new ModelAndView("index");
    }
    @GetMapping("/")
    public ModelAndView viewHomePage(Model model) {
        List<Annonce> listAnnonces = annonceService.findAll();
        model.addAttribute("listAnnonces", listAnnonces);

        return new ModelAndView("index");
    }
    @GetMapping("/annonces/{id}")
    public ModelAndView getAnnonceById(@PathVariable Long id, Model model) {
        Annonce annonce = annonceService.getAnnonceById(id);
        User user = userService.getUserById(annonce.getUser().getId());
        model.addAttribute("annonce", annonce);
        model.addAttribute("user", user);

        return new ModelAndView("annonce");
    }



    @GetMapping("/login")
    public ModelAndView viewLoginPage(Model model) {
        List<Annonce> listAnnonces = annonceService.findAll();
        model.addAttribute("listAnnonces", listAnnonces);

        return new ModelAndView("login");
    }
    @GetMapping("/register")
    public String viewRegisterPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/process_register")
    public RedirectView processRegister(User user, RedirectAttributes re) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);


    try {
        user.setRole(UserRole.USER);
        userService.createUser(user);
            RedirectView rv = new RedirectView("/login", true);
            re.addFlashAttribute("message", "You have successfully registered. Please login.");
            return rv;
        }catch (Exception e){
            RedirectView rv = new RedirectView("/register", true);
            re.addFlashAttribute("error", e.getMessage());
            return rv;
        }
    }


    @GetMapping("/admin")
    public ModelAndView viewAdminPage(Model model) {
            List<Annonce> listAnnonces = annonceService.findAll();
            model.addAttribute("listAnnonces", listAnnonces);

            return new ModelAndView("admin");

    }



    @GetMapping("/admin/annonce/edit/{id}")
    public ModelAndView viewEditAnnPage(Model model, @PathVariable Long id) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        Annonce annonce = annonceService.getAnnonceById(id);
        model.addAttribute("annonce", annonce);
        return new ModelAndView("edit");



    }

    @GetMapping("/admin/annonce/delete/{id}")
    public ModelAndView viewDelAnnPage(Model model, @PathVariable Long id) {
        annonceService.deleteAnnonce(id);
        return new ModelAndView("admin");

    }
    @GetMapping("/admin/annonce/add")
    public String viewAddAnnPage(Model model) {

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        Annonce annonce = new Annonce();
        model.addAttribute("annonce", annonce);

            return "add";

    }

    @PostMapping("/admin/addListing")
    public RedirectView processAddAnn(@RequestParam("image") MultipartFile image, Annonce annonce, RedirectAttributes re) {

        try {

            annonce.getUser().setId(userService.getByEmail(annonce.getUser().getEmail()).getId());
           Annonce annonce1 = annonceService.createAnnonce(annonce);

            try {
                byte[] imageBytes = image.getBytes();
                String fileName = image.getOriginalFilename();

                Path path = Paths.get("path/to/save/"+annonce1.getId()+".jpg");
                Files.write(path, imageBytes);
                annonce1.setPhotos(path.toString());
                annonceService.updateAnnonce(annonce1.getId(), annonce1);
            } catch (IOException e) {
                e.printStackTrace();
            }


            RedirectView rv = new RedirectView("/admin", true);
            re.addFlashAttribute("message", "You have successfully registered. Please login.");
            return rv;
        }catch (Exception e){
            RedirectView rv = new RedirectView("/admin/annonce/add", true);
            re.addFlashAttribute("error", e.getMessage());
            return rv;
        }

    }


    @PostMapping("/admin/saveEdit")
    public RedirectView saveSubmit(@ModelAttribute Annonce annonce, Model model,RedirectAttributes re ) {


        model.addAttribute("annonce", annonce);

        try {
            annonceService.updateAnnonce(annonce.getId(), annonce);
            RedirectView rv = new RedirectView("/admin", true);
            re.addFlashAttribute("message", "You have successfully registered. Please login.");
            return rv;
        }catch (Exception e){

            RedirectView rv = new RedirectView("/admin/annonce/edit/"+annonce.getId() , true);
            re.addFlashAttribute("error", e.getMessage());
            return rv;
        }

    }


    @GetMapping("/profile")
    public String viewProfilePage() {
        return "profile";
    }



    @PutMapping("/{id}")
    public Annonce updateAnnonce(@PathVariable Long id, @RequestBody Annonce annonce) {
        Annonce annonceToUpdate = annonceRepository.findById(id).get();
        annonceToUpdate.setName(annonce.getName());
        annonceToUpdate.setDescription(annonce.getDescription());
        annonceToUpdate.setPrix(annonce.getPrix());
        annonceToUpdate.setSuperficie(annonce.getSuperficie());
        annonceToUpdate.setLocalisation(annonce.getLocalisation());
        annonceToUpdate.setPhotos(annonce.getPhotos());
        annonceToUpdate.setUser(annonce.getUser());

        return annonceRepository.save(annonceToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteAnnonce(@PathVariable Long id) {
        annonceRepository.deleteById(id);
    }

    @GetMapping("/annonces")
    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }


    @PostMapping("/upload-image")
    public String handleImageUpload(@RequestParam("image") MultipartFile image) {
        //handle image upload

        try {
            byte[] imageBytes = image.getBytes();
            String fileName = image.getOriginalFilename();

            Path path = Paths.get("path/to/save/image.jpg");
            Files.write(path, imageBytes);
            //store the file path in the database
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/success";

    }




}

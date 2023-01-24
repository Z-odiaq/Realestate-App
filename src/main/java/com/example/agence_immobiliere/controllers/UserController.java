package com.example.agence_immobiliere.controllers;

import com.example.agence_immobiliere.entities.User;
import com.example.agence_immobiliere.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateur")

public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUtilisateurs() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUtilisateurById(@PathVariable Long id) {
        return userRepository.findById(id).get();
    }

    @PostMapping
    public User createUtilisateur(@RequestBody User utilisateur) {
        return userRepository.save(utilisateur);
    }

    @PutMapping("/{id}")
    public User updateUtilisateur(@PathVariable Long id, @RequestBody User utilisateur) {
        User utilisateurToUpdate = userRepository.findById(id).get();
        utilisateurToUpdate.setNom(utilisateur.getNom());
        utilisateurToUpdate.setPrenom(utilisateur.getPrenom());
        utilisateurToUpdate.setAdresse(utilisateur.getAdresse());
        utilisateurToUpdate.setTelephone(utilisateur.getTelephone());
        utilisateurToUpdate.setEmail(utilisateur.getEmail());
        utilisateurToUpdate.setPassword(utilisateur.getPassword());
        utilisateurToUpdate.setRole(utilisateur.getRole());
        utilisateurToUpdate.setAnnonces(utilisateur.getAnnonces());

        return userRepository.save(utilisateurToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteUtilisateur(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
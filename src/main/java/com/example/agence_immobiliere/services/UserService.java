package com.example.agence_immobiliere.services;

import com.example.agence_immobiliere.entities.User;
import com.example.agence_immobiliere.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User utilisateur) {

        if (userRepository.findByEmail(utilisateur.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }
        if (utilisateur.getPassword().isBlank()) {
            throw new RuntimeException("Please enter password");
        }


        return userRepository.save(utilisateur);
    }

    public User updateUser(Long id, User utilisateur) {
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

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}


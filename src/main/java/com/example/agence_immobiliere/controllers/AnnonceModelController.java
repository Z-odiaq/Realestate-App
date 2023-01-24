package com.example.agence_immobiliere.controllers;

import com.example.agence_immobiliere.entities.Annonce;
import com.example.agence_immobiliere.repositories.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annonce")

public class AnnonceModelController {

    @Autowired
    private AnnonceRepository annonceRepository;


    @GetMapping
    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

    @GetMapping("/{id}")
    public Annonce getAnnonceById(@PathVariable Long id) {
        return annonceRepository.findById(id).get();
    }

    @PostMapping
    public Annonce createAnnonce(@RequestBody Annonce annonce) {
        return annonceRepository.save(annonce);
    }

    @PutMapping("/{id}")
    public Annonce updateAnnonce(@PathVariable Long id, @RequestBody Annonce annonce) {
        Annonce annonceToUpdate = annonceRepository.findById(id).get();
        annonceToUpdate.setName(annonce.getName());
        annonceToUpdate.setDescription(annonce.getDescription());
        annonceToUpdate.setPrix(annonce.getPrix());
        annonceToUpdate.setDisponibilite(annonce.isDisponibilite());
        annonceToUpdate.setNombre_pieces(annonce.getNombre_pieces());
        annonceToUpdate.setSuperficie(annonce.getSuperficie());
        annonceToUpdate.setLocalisation(annonce.getLocalisation());
        annonceToUpdate.setPhotos(annonce.getPhotos());
        annonceToUpdate.setContact(annonce.getContact());
        annonceToUpdate.setType(annonce.getType());

        return annonceRepository.save(annonceToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteAnnonce(@PathVariable Long id) {
        annonceRepository.deleteById(id);
    }
}


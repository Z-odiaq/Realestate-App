package com.example.agence_immobiliere.services;

import com.example.agence_immobiliere.entities.Annonce;
import com.example.agence_immobiliere.repositories.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;

    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }


    public Annonce getAnnonceById(Long id) {
        return annonceRepository.findById(id).get();
    }


    public Annonce createAnnonce(Annonce annonce) {



        return annonceRepository.save(annonce);
    }

    public Annonce updateAnnonce(Long id, Annonce annonce) {
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

    public void deleteAnnonce(Long id) {
        annonceRepository.deleteById(id);
    }

    public List<Annonce> findAll() {
        return annonceRepository.findAll();
    }
}

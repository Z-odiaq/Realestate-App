package com.example.agence_immobiliere.entities;

import lombok.*;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "contact")
    private String contact;

    @Column(name = "photos")
    private String photos;

    @Column(name = "localisation")
    private String localisation;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "disponibilite")
    private boolean disponibilite;
    @Column(name = "prix")
    private double prix;

    @Column(name = "date")
    private Date date;

    @Column(name = "surface")
    private double superficie;

    @Column(name = "nombre_pieces")
    private int nombre_pieces;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

}
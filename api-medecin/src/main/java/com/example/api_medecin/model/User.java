package com.example.api_medecin.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class User {



    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "telephone", unique = true, nullable = false, length = 10)
    private String telephone;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email obligatoire")
    @Email(message = "Format email invalide")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Mot de passe obligatoire")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;



    // Constructeurs
    public User() {}

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters et Setters

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
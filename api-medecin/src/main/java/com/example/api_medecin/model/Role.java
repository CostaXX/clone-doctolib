package com.example.api_medecin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name; // ex: "ROLE_MEDECIN", "ROLE_UTILISATEUR", "ROLE_CABINET"

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // getters / setters
}
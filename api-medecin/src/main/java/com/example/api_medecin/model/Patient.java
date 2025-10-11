package com.example.api_medecin.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.Check;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
// @Table(name = "patient")
@Check(constraints = "sexe IN (0,1,2)")
public class Patient extends User {



    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "sexe", nullable = false)
    private Integer sexe;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<RendezVous> rendezVous;

    // Getters and Setters

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Integer getSexe() {
        return sexe;
    }

    public void setSexe(Integer sexe) {
        this.sexe = sexe;
    }
    
}
package com.example.api_medecin.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "medecin")
public class Medecin extends User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medecin_id;


    @OneToMany(mappedBy = "medecin", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<RendezVous> rendezVous;

    @Column(name = "specialite", nullable = false)
    private String specialite;

    @Column(name = "rpps", unique = true, nullable = false)
    private String rpps;

    public Long getMedecin_id() {
        return medecin_id;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getRpps() {
        return rpps;
    }

    public void setRpps(String rpps) {
        this.rpps = rpps;
    }
    

}
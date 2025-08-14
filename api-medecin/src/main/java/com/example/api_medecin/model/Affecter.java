package com.example.api_medecin.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "affecter")
public class Affecter {
    
    @EmbeddedId
    private AffecterId id;

    @ManyToOne
    @MapsId("medecinId")
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;

    @ManyToOne
    @MapsId("cabinetId")
    @JoinColumn(name = "cabinet_id")
    private Cabinet cabinet;

    // Getters and Setters
    public AffecterId getId() {
        return id;
    }

    public void setId(AffecterId id) {
        this.id = id;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public Cabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(Cabinet cabinet) {
        this.cabinet = cabinet;
    }

}
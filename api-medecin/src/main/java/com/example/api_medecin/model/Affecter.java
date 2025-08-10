package com.example.api_medecin.model;

import jakarta.persistence.Column;
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

    @Column(name = "telephone", nullable = false)
    private String telephone;
}
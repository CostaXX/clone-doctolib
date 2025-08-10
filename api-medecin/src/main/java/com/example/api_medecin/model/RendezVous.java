package com.example.api_medecin.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "rendez_vous")
public class RendezVous {
    
    @EmbeddedId
    private RendezVousId id;

    @ManyToOne
    @MapsId("medecinId")
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;

    @ManyToOne
    @MapsId("patientId")
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "date", nullable = false)
    private LocalDateTime dateHeure;


}

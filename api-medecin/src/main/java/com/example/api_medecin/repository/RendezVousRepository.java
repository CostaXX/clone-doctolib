package com.example.api_medecin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_medecin.model.RendezVous;
import com.example.api_medecin.model.RendezVousId;

public interface RendezVousRepository extends JpaRepository<RendezVous, RendezVousId> {
    // Accès via les propriétés de la clé composite
    List<RendezVous> findByIdMedecinId(Long medecinId);
    List<RendezVous> findByIdPatientId(Long patientId);
    List<RendezVous> findByIdCabinetId(Long cabinetId);

}

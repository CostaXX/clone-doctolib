package com.example.api_medecin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.api_medecin.model.RendezVous;

public interface RendezVousRepository extends CrudRepository<RendezVous, Long> {
    List<RendezVous> findByMedecinId(Long medecinId);
    List<RendezVous> findByMedecinIdAndPatientIdIsNull(Long medecinId);
}
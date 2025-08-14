package com.example.api_medecin.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_medecin.model.Horaire;

public interface HoraireRepository extends JpaRepository<Horaire, Long> {
    
    List<Horaire> findByJour(String jour);
    List<Horaire> findByHeureDebutBetween(LocalTime start, LocalTime end);
    List<Horaire> findByHeureFinBetween(LocalTime start, LocalTime end);
    
    // Autres méthodes de recherche si nécessaire
    // Par exemple, tu peux ajouter des méthodes pour rechercher par jour, heure de début, etc.
    
}
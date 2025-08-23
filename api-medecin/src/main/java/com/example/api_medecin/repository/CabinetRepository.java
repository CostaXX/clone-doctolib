package com.example.api_medecin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_medecin.model.Cabinet;

public interface CabinetRepository extends JpaRepository<Cabinet, Long> {
    
    List<Cabinet> findByNom(String nom);
    List<Cabinet> findByAdresse(String adresse);
    List<Cabinet> findByTelephone(String telephone);
    List<Cabinet> findByHoraireId(Long horaireId);
    boolean existsByEmail(String email);
    // Autres méthodes de recherche si nécessaire
    // Par exemple, tu peux ajouter des méthodes pour rechercher par nom, adresse, etc.
    
}

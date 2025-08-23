package com.example.api_medecin.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.api_medecin.model.Medecin;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    // Ici tu peux ajouter des méthodes personnalisées si besoin
    List<Medecin> findByNom(String nom);
    List<Medecin> findByPrenom(String prenom);
    List<Medecin> findBySpecialite(String specialite);
    Boolean existsByEmail(String email);
    ResponseEntity<Medecin> findByTelephone(String telephone);
    ResponseEntity<Medecin> findByRpps(String rpps);

    // Autres méthodes de recherche si nécessaire
}

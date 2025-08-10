package com.example.api_medecin.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.api_medecin.model.Medecin;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    // Ici tu peux ajouter des méthodes personnalisées si besoin
    List<Medecin> findBySpecialite(String specialite);
    ResponseEntity<Medecin> findByTelephone(String telephone);
    ResponseEntity<Medecin> findByRpps(String rpps);

    // Autres méthodes de recherche si nécessaire
}

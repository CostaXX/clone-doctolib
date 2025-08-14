package com.example.api_medecin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.api_medecin.model.Affecter;
import com.example.api_medecin.model.AffecterId;

public interface AffecterRepository extends JpaRepository<Affecter, AffecterId> {
    // Accès via les propriétés de la clé composite
    List<Affecter> findByIdMedecinId(Long medecinId);

    List<Affecter> findByIdCabinetId(Long cabinetId);

    List<Affecter> findByMedecinId(Long medecinId); 
    List<Affecter> findByCabinetId(Long cabinetId); 

}

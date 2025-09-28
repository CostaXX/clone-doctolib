package com.example.api_medecin.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.api_medecin.model.Medecin;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    
}

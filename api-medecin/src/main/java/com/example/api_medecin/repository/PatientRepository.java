package com.example.api_medecin.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api_medecin.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{

    List<Patient> findByNom(String nom);
    List<Patient> findByPrenom(String prenom);
    List<Patient> findByEmail(String email);
    Boolean existsByEmail(String email);
    List<Patient> findByTelephone(String telephone);
    List<Patient> findByDateNaissance(LocalDate dateNaissance);
    List<Patient> findBySexe(Integer sexe);
    
}

package com.example.api_medecin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_medecin.model.Patient;
import com.example.api_medecin.repository.PatientRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/patients")
public class PatientController {
    
    private final PatientRepository patientRepository;
    
    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient saved = patientRepository.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // READ all
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    
    // READ one
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return patientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        return patientRepository.findById(id)
            .map(patient -> {
                patient.setNom(patientDetails.getNom());
                patient.setPrenom(patientDetails.getPrenom());
                patient.setDateNaissance(patientDetails.getDateNaissance());
                patient.setSexe(patientDetails.getSexe());
                patient.setTelephone(patientDetails.getTelephone());
                patient.setEmail(patientDetails.getEmail());
                patient.setPassword(patientDetails.getPassword());
                Patient updated = patientRepository.save(patient);
                return ResponseEntity.ok(updated);
            }).orElse(ResponseEntity.notFound().build());
    }
    
    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        if (!patientRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        patientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

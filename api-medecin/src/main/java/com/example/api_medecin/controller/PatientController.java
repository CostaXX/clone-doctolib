package com.example.api_medecin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_medecin.model.Patient;
import com.example.api_medecin.model.AuthResponse;
import com.example.api_medecin.repository.PatientRepository;
import com.example.api_medecin.service.AuthService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/patients")
public class PatientController {
    
    private final PatientRepository patientRepository;
    private final AuthService authService;

    
    public PatientController(PatientRepository patientRepository, AuthService authService) {
        this.patientRepository = patientRepository;
        this.authService = authService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<AuthResponse> createPatient(@RequestBody Patient patient) {
        Patient saved = (Patient) authService.register(patient);
        String token = authService.generateToken(saved.getEmail(), saved.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token, saved));
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

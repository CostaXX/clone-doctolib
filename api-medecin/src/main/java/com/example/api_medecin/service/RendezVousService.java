package com.example.api_medecin.service;

import org.springframework.stereotype.Service;

import com.example.api_medecin.model.Patient;
import com.example.api_medecin.model.RendezVous;
import com.example.api_medecin.model.User;
import com.example.api_medecin.repository.PatientRepository;
import com.example.api_medecin.repository.RendezVousRepository;
import com.example.api_medecin.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RendezVousService {

    private final PatientRepository patientRepository;
    private final RendezVousRepository rendezVousRepository;
    
    public void GetRendezVous(String emailUsername, long rendezVousId) {
        RendezVous rendezVous = rendezVousRepository.findById(rendezVousId).orElseThrow();
        rendezVous.setPatient(patientRepository.findByEmail(emailUsername));
        rendezVousRepository.save(rendezVous);
    }
}

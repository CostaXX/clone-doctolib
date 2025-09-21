package com.example.api_medecin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_medecin.model.RendezVous;
import com.example.api_medecin.model.RendezVousId;
import com.example.api_medecin.repository.RendezVousRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/rendezvous")
public class RendezVousController {
    
    private final RendezVousRepository rendezVousRepository;

    public RendezVousController(RendezVousRepository rendezVousRepository) {
        this.rendezVousRepository = rendezVousRepository;
    }

    // CREATE
    @PostMapping
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<RendezVous> createRendezVous(@RequestBody RendezVous rendezVous) {
        RendezVous saved = rendezVousRepository.save(rendezVous);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/patients/{patientId}/rendezvous")
    @PreAuthorize("(hasRole('PATIENT') and #id == authentication.name)")
    public ResponseEntity<RendezVous> getRendezVousByPatientId(@PathVariable Long patientId) {
        return rendezVousRepository.findById(new RendezVousId(null, patientId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/medecins/{medecinId}/rendezvous")
    @PreAuthorize("hasRole('MEDECIN') and #id == authentication.name")
    public ResponseEntity<RendezVous> getRendezVousByMedecinId(@PathVariable Long medecinId) {
        return rendezVousRepository.findById(new RendezVousId(medecinId, null))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/patients/{patientId}/rendezvous/{medecinId}")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.name")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long patientId, @PathVariable Long medecinId) {
        RendezVousId rendezVousId = new RendezVousId(medecinId, patientId);
        if (rendezVousRepository.existsById(rendezVousId)) {
            rendezVousRepository.deleteById(rendezVousId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/medecins/{medecinId}/rendezvous/{patientId}")
    @PreAuthorize("hasRole('MEDECIN') and #id == authentication.name")
    public ResponseEntity<Void> deleteRendezVousByMedecin(@PathVariable Long medecinId, @PathVariable Long patientId) {
        RendezVousId rendezVousId = new RendezVousId(medecinId, patientId);
        if (rendezVousRepository.existsById(rendezVousId)) {
            rendezVousRepository.deleteById(rendezVousId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

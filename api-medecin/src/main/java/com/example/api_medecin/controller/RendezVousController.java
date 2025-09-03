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

@RestController
@RequestMapping("/rendezvous")
public class RendezVousController {
    
    private final RendezVousRepository rendezVousRepository;

    public RendezVousController(RendezVousRepository rendezVousRepository) {
        this.rendezVousRepository = rendezVousRepository;
    }

    // CREATE
    @PostMapping
    @PreAuthorize("")
    public ResponseEntity<RendezVous> createRendezVous(@RequestBody RendezVous rendezVous) {
        RendezVous saved = rendezVousRepository.save(rendezVous);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/patients/{patientId}/rendezvous")
    public ResponseEntity<RendezVous> getRendezVousByPatientId(@PathVariable Long patientId) {
        return rendezVousRepository.findById(new RendezVousId(null, patientId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ all
    @GetMapping
    @PreAuthorize("")
    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }
    
    @GetMapping("/{medecinId}/{patientId}")
    public ResponseEntity<RendezVous> getRendezVousById(@PathVariable Long medecinId, @PathVariable Long patientId) {
        return rendezVousRepository.findById(new RendezVousId(medecinId, patientId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{medecinId}/{patientId}")
    public ResponseEntity<RendezVous> updateRendezVous(@PathVariable Long medecinId, @PathVariable Long patientId, @RequestBody RendezVous rendezVousDetails) {
        return rendezVousRepository.findById(new RendezVousId(medecinId, patientId))
            .map(rendezVous -> {
                rendezVous.setMedecin(rendezVousDetails.getMedecin());
                rendezVous.setPatient(rendezVousDetails.getPatient());
                rendezVous.setDateHeure(rendezVousDetails.getDateHeure());
                return ResponseEntity.ok(rendezVousRepository.save(rendezVous));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{medecinId}/{patientId}")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long medecinId, @PathVariable Long patientId) {
        if (rendezVousRepository.existsById(new RendezVousId(medecinId, patientId))) {
            rendezVousRepository.deleteById(new RendezVousId(medecinId, patientId));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

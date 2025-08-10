package com.example.api_medecin.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import com.example.api_medecin.model.Medecin;
import com.example.api_medecin.repository.MedecinRepository;

@RestController
@RequestMapping("/medecins")
public class MedecinController {

    private final MedecinRepository medecinRepository;

    public MedecinController(MedecinRepository medecinRepository) {
        this.medecinRepository = medecinRepository;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Medecin> createMedecin(@RequestBody Medecin medecin) {
        Medecin saved = medecinRepository.save(medecin);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // READ all
    @GetMapping
    public List<Medecin> getAllMedecins() {
        return medecinRepository.findAll();
    }

    // READ one
    @GetMapping("/{id}")
    public ResponseEntity<Medecin> getMedecinById(@PathVariable Long id) {
        return medecinRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Medecin> updateMedecin(@PathVariable Long id, @RequestBody Medecin medecinDetails) {
        return medecinRepository.findById(id)
            .map(medecin -> {
                medecin.setNom(medecinDetails.getNom());
                medecin.setPrenom(medecinDetails.getPrenom());
                // mets à jour tous les champs que tu veux ici
                Medecin updated = medecinRepository.save(medecin);
                return ResponseEntity.ok(updated);
            }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedecin(@PathVariable Long id) {
        if (!medecinRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        medecinRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
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
import com.example.api_medecin.model.Affecter;
import com.example.api_medecin.model.AffecterId;
import com.example.api_medecin.repository.AffecterRepository;

@RestController
@RequestMapping("/affecters")
public class AffecterController {
    
    
    private final AffecterRepository affecterRepository;

    public AffecterController(AffecterRepository affecterRepository) {
        this.affecterRepository = affecterRepository;
    }

    @PostMapping
    public ResponseEntity<Affecter> create(@RequestBody Affecter affecter) {
        Affecter saved = affecterRepository.save(affecter);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public List<Affecter> getAll() {
        return affecterRepository.findAll();
    }

    @GetMapping("/{medecinId}/{cabinetId}")
    public Affecter getOne(@PathVariable Long medecinId, @PathVariable Long cabinetId) {
        return affecterRepository.findById(new AffecterId(medecinId, cabinetId))
                .orElseThrow(() -> new RuntimeException("Affectation non trouvée"));
    }

    @PutMapping("/{medecinId}/{cabinetId}")
    public ResponseEntity<Affecter> update(@PathVariable Long medecinId, @PathVariable Long cabinetId, @RequestBody Affecter affecterDetails) {
        return affecterRepository.findById(new AffecterId(medecinId, cabinetId))
            .map(affecter -> {
                affecter.setMedecin(affecterDetails.getMedecin());
                affecter.setCabinet(affecterDetails.getCabinet());
                // mets à jour tous les champs que tu veux ici
                Affecter updated = affecterRepository.save(affecter);
                return ResponseEntity.ok(updated);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{medecinId}/{cabinetId}")
    public ResponseEntity<Void> delete(@PathVariable Long medecinId, @PathVariable Long cabinetId) {
        if (!affecterRepository.existsById(new AffecterId(medecinId, cabinetId))) {
            return ResponseEntity.notFound().build(); 
        }

        affecterRepository.deleteById(new AffecterId(medecinId, cabinetId));
        return ResponseEntity.noContent().build();
    }
}

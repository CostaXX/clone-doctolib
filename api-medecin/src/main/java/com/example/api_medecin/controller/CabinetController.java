package com.example.api_medecin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_medecin.model.AuthResponse;
import com.example.api_medecin.model.Cabinet;
import com.example.api_medecin.repository.CabinetRepository;
import com.example.api_medecin.service.AuthService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/cabinets")
public class CabinetController {
    private final CabinetRepository cabinetRepository;
    private final AuthService authService;

    public CabinetController(CabinetRepository cabinetRepository, AuthService authService) {
        this.cabinetRepository = cabinetRepository;
        this.authService = authService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<AuthResponse> createCabinet(@RequestBody Cabinet cabinet) {
        Cabinet saved = (Cabinet) authService.register(cabinet);
        String token = authService.generateToken(saved.getId(), saved.getEmail(), saved.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token));
    }

    // READ all
    @GetMapping
    public List<Cabinet> getAllCabinets() {
        return cabinetRepository.findAll();
    }
    
    // READ one
    @GetMapping("/{id}")
    public ResponseEntity<Cabinet> getCabinetById(@PathVariable Long id) {
        return cabinetRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CABINET') and #id == authentication.name")
    public ResponseEntity<Cabinet> updateCabinet(@PathVariable Long id, @RequestBody Cabinet cabinetDetails) {
        return cabinetRepository.findById(id)
            .map(cabinet -> {
                cabinet.setNom(cabinetDetails.getNom());
                cabinet.setAdresse(cabinetDetails.getAdresse());
                // mets à jour tous les champs que tu veux ici
                Cabinet updated = cabinetRepository.save(cabinet);
                return ResponseEntity.ok(updated);
            }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CABINET') and #id == authentication.name")
    public ResponseEntity<Void> deleteCabinet(@PathVariable Long id) {
        if (!cabinetRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cabinetRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
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
import com.example.api_medecin.model.Horaire;

import com.example.api_medecin.repository.HoraireRepository;

@RestController
@RequestMapping("/horaires")
public class HoraireController {
    

    private final HoraireRepository horaireRepository;

    public HoraireController(HoraireRepository horaireRepository) {
        this.horaireRepository = horaireRepository;
    }

    @PostMapping
    public ResponseEntity<Horaire> create(@RequestBody Horaire horaire) {
        Horaire saved = horaireRepository.save(horaire);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public List<Horaire> getAll() {
        return horaireRepository.findAll();
    }

    @GetMapping("/{id}")
    public Horaire getOne(@PathVariable Long id) {
        return horaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horaire non trouvé"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Horaire> update(@PathVariable Long id, @RequestBody Horaire horaireDetails) {
        return horaireRepository.findById(id)
            .map(horaire -> {
                horaire.setJour(horaireDetails.getJour());
                horaire.setHeureDebut(horaireDetails.getHeureDebut());
                horaire.setHeureFin(horaireDetails.getHeureFin());
                Horaire updated = horaireRepository.save(horaire);
                return ResponseEntity.ok(updated);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!horaireRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        horaireRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}

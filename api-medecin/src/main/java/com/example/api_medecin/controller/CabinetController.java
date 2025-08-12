package com.example.api_medecin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_medecin.model.Cabinet;
import com.example.api_medecin.repository.CabinetRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/cabinets")
public class CabinetController {
    private final CabinetRepository cabinetRepository;

    public CabinetController(CabinetRepository cabinetRepository) {
        this.cabinetRepository = cabinetRepository;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Cabinet> createCabinet(@RequestBody Cabinet cabinet) {
        Cabinet saved = cabinetRepository.save(cabinet);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // READ all
    @GetMapping
    public List<Cabinet> getAllCabinets() {
        return cabinetRepository.findAll();
    }
    
}

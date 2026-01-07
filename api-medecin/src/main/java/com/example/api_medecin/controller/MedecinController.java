package com.example.api_medecin.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.api_medecin.dto.response.RendezVousByDateDTO;
import com.example.api_medecin.model.Medecin;
import com.example.api_medecin.model.RendezVous;
import com.example.api_medecin.repository.MedecinRepository;
import com.example.api_medecin.repository.RendezVousRepository;
import com.example.api_medecin.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/medecins")
@RequiredArgsConstructor
public class MedecinController {

    private final RendezVousRepository rendezVousRepository;

    @GetMapping("/{medecinId}/rendezvous")
    public List<RendezVousByDateDTO> getRendezVousByMedecinId(@PathVariable Long medecinId) {
        List<RendezVous> rendezVous = rendezVousRepository.findByMedecinIdAndPatientIdIsNull(medecinId);
        Map<LocalDate, List<LocalTime>> map = new HashMap<>();
        for (RendezVous rv : rendezVous) {
            LocalDateTime dateTime = rv.getDateHeure();
            map.computeIfAbsent(dateTime.toLocalDate(), d -> new ArrayList<>())
            .add(dateTime.toLocalTime());
        }

        List<RendezVousByDateDTO> rendezVousByDateDTOs = map.entrySet().stream()
        .map(e -> new RendezVousByDateDTO(e.getKey(), e.getValue()))
        .toList();
        return rendezVousByDateDTOs;
    }
    
}
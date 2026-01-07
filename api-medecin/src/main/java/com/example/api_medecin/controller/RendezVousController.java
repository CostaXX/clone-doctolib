package com.example.api_medecin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_medecin.model.Patient;
import com.example.api_medecin.model.RendezVous;
import com.example.api_medecin.service.JwtService;
import com.example.api_medecin.service.RendezVousService;
import com.example.api_medecin.repository.RendezVousRepository;
import com.example.api_medecin.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
public class RendezVousController {

    private final JwtService jwtService;
    private final RendezVousService rendezVousService;



    @PutMapping("/{rendezVousId}")
    public void getRendezVousWithMedecin(@PathVariable long rendezVousId, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String emailUsername = jwtService.extractUsername(authHeader.substring(7));
        rendezVousService.GetRendezVous(emailUsername, rendezVousId);
    }
    

}
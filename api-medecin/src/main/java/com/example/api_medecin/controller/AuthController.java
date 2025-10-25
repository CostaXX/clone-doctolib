package com.example.api_medecin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_medecin.dto.request.AuthentificationDTO;
import com.example.api_medecin.model.Medecin;
import com.example.api_medecin.model.Patient;
import com.example.api_medecin.model.User;
import com.example.api_medecin.service.AuthService;
import com.example.api_medecin.service.JwtService;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService utilisateurService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping(path = "connexion")
    public Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
        );

        if(authenticate.isAuthenticated()) {
            return this.jwtService.generate(authentificationDTO.username());
        }
        return null;
    }

    @PostMapping(path = "inscription")
    public void inscription(@RequestBody User utilisateur) {
        if (utilisateur instanceof Medecin) {
        Medecin medecin = (Medecin) utilisateur;
        this.utilisateurService.inscription(medecin);
        // traiter le medecin
    } else if (utilisateur instanceof Patient) {
        Patient patient = (Patient) utilisateur;
        this.utilisateurService.inscription(patient);
        // traiter le patient
    }
        // log.info("Inscription");
        
    }
    

    @PostMapping("register/medecin")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }

    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String, String> activation) {
        this.utilisateurService.activation(activation);
    }    
    
}

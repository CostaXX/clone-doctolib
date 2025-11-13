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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;





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

    @PostMapping(path = "deconnexion")
    public void deconnexion() {
        this.jwtService.deconnexion();
    }

    @PostMapping(path = "inscription")
    public void inscription(@RequestBody User utilisateur) {
    if (utilisateur instanceof Medecin) {
        Medecin medecin = (Medecin) utilisateur;
        this.utilisateurService.inscription(medecin);

    } else if (utilisateur instanceof Patient) {
        Patient patient = (Patient) utilisateur;
        this.utilisateurService.inscription(patient);

    }
        // log.info("Inscription");
        
    }

    @GetMapping("helloWorld")
    public String getMethodName(@RequestParam Map<String, String> param) {
        return new String();
    }
    

    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String, String> activation) {
        this.utilisateurService.activation(activation);
    }

    @PostMapping(path = "refresh-token")
    public @ResponseBody Map<String, String> refreshToken(@RequestBody Map<String, String> refreshTokenRequest) {
        return this.jwtService.refreshToken(refreshTokenRequest);
    }

    
    
}

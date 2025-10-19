package com.example.api_medecin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_medecin.dto.request.AuthentificationDTO;

import com.example.api_medecin.dto.request.PatientRegisterRequest;

import com.example.api_medecin.dto.response.AuthResponse;
import com.example.api_medecin.repository.UserRepository;
import com.example.api_medecin.service.AuthService;
import com.example.api_medecin.service.JwtService;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final AuthService utilisateurService;
    private final AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @PostMapping("login")
        public Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
        );

        if(authenticate.isAuthenticated()) {
            return this.jwtService.generate(authentificationDTO.username());
        }
        return null;
    }

    @PostMapping("register/patient")
    public ResponseEntity<?> register(@RequestBody PatientRegisterRequest user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }
        AuthResponse authResponse = authService.registerPatient(user);
        return ResponseEntity.ok(authResponse);
    }
    

    @PostMapping("register/medecin")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    

    // @PostMapping("logout")
    // public ResponseEntity<MessageResponse> logout(HttpServletRequest request) {
    //     return new String();
    // }

    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String, String> activation) {
        this.utilisateurService.activation(activation);
    }    
    
}

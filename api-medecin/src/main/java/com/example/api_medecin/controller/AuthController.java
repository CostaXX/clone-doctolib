package com.example.api_medecin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_medecin.dto.request.LoginRequest;
import com.example.api_medecin.dto.request.RefreshTokenRequest;
import com.example.api_medecin.dto.request.PatientRegisterRequest;
import com.example.api_medecin.dto.response.MessageResponse;
import com.example.api_medecin.dto.response.TokenResponse;
import com.example.api_medecin.dto.response.AuthResponse;
import com.example.api_medecin.model.User;
import com.example.api_medecin.repository.PatientRepository;
import com.example.api_medecin.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/authentication")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        
        AuthResponse response = authService.login(request);

        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("token", response.getToken());
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("register/patient")
    public ResponseEntity<AuthResponse> register(@RequestBody PatientRegisterRequest request) {
        return ResponseEntity.ok(authService.registerPatient(request));
    }

    @PostMapping("register/medecin")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    

    @PostMapping("logout")
    public ResponseEntity<MessageResponse> logout(HttpServletRequest request) {
        return new String();
    }

    @PostMapping("refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return new String();
    }
    
    @GetMapping("users/me")
    public ResponseEntity<User> getProfile(@RequestParam AuthResponse request) {
        return new String();
    }
    
    
}

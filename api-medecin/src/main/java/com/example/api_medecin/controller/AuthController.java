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
import com.example.api_medecin.model.Patient;
import com.example.api_medecin.model.User;
import com.example.api_medecin.repository.PatientRepository;
import com.example.api_medecin.repository.UserRepository;
import com.example.api_medecin.service.AuthService;
import com.example.api_medecin.service.RefreshTokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest user) {
        
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            AuthResponse authResponse = authService.login(user);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("register/patient")
    public ResponseEntity<?> register(@RequestBody PatientRegisterRequest user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }
        AuthResponse authResponse = authService.registerPatient(user);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("helloWorld")
    public String getMethodName() {
        return "Hello world";
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

    @PostMapping("refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenResponse tokenResponse = refreshTokenService.refreshToken(request);
        return ResponseEntity.ok(tokenResponse);
    }
    
    // @GetMapping("users/me")
    // public ResponseEntity<User> getProfile(@RequestParam AuthResponse request) {
    //     return new String();
    // }
    
    
}

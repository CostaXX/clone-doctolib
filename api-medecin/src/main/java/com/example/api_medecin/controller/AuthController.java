package com.example.api_medecin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_medecin.dto.LoginRequest;
import com.example.api_medecin.dto.MessageResponse;
import com.example.api_medecin.dto.RefreshTokenRequest;
import com.example.api_medecin.dto.RegisterRequest;
import com.example.api_medecin.dto.TokenResponse;
import com.example.api_medecin.model.AuthResponse;
import com.example.api_medecin.model.User;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/authentication")
public class AuthController {
    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        
        return entity;
    }

    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
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

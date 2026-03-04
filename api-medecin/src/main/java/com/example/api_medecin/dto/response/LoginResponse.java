package com.example.api_medecin.dto.response;

import java.util.Map;

import com.example.api_medecin.model.User;

public class LoginResponse {
    private User user;
    private Map<String, String> tokens;  // Les tokens (optionnel si en cookies)
    
    // Constructeur, getters, setters
    public LoginResponse(User user, Map<String, String> tokens) {
        this.user = user;
        this.tokens = tokens;
    }
}

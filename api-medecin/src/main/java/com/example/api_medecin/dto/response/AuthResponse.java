package com.example.api_medecin.dto.response;

import java.util.Map;

import com.example.api_medecin.model.User;

public class AuthResponse {
    private Object user;
    private Map<String, String> tokens;  // Les tokens (optionnel si en cookies)
    
    public AuthResponse() {}
    
    public AuthResponse(Object user, Map<String, String> tokens) {
        this.user = user;
        this.tokens = tokens;
    }
    
    public Object getUser() {
        return user;
    }
    
    public void setUser(Object user) {
        this.user = user;
    }
    
    public Map<String, String> getTokens() {
        return tokens;
    }
    
    public void setTokens(Map<String, String> tokens) {
        this.tokens = tokens;
    }
}

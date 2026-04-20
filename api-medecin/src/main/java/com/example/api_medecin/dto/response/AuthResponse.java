package com.example.api_medecin.dto.response;

import java.util.Map;

import com.example.api_medecin.model.User;

public class AuthResponse {
    private Object user;
    
    public AuthResponse() {}
    
    public AuthResponse(Object user) {
        this.user = user;
    }
    
    public Object getUser() {
        return user;
    }
    
    public void setUser(Object user) {
        this.user = user;
    }
}

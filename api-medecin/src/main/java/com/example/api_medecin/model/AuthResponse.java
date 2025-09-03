package com.example.api_medecin.model;

public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    // public User getUser() {
    //     return user;
    // }

    public String getToken() {
        return token;
    }

    

}
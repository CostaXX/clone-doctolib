package com.example.api_medecin.dto.request;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequest {
    @NotBlank(message = "Le token de rafraîchissement est obligatoire")
    private String refreshToken;

    // Constructeurs
    public RefreshTokenRequest() {}

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // Getters et Setters
    public String getRefreshToken() {
        return refreshToken;
    }
}

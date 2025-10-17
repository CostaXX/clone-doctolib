package com.example.api_medecin.service;

import org.springframework.stereotype.Service;

import com.example.api_medecin.dto.response.TokenResponse;
import com.example.api_medecin.dto.request.RefreshTokenRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    public TokenResponse refreshToken(RefreshTokenRequest refreshToken) {
        // Logic to validate and refresh the token
        return null; // Replace with actual implementation
    }
}

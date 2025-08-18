package com.example.api_medecin.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.web.webauthn.api.AuthenticatorResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.api_medecin.repository.CabinetRepository;
import com.example.api_medecin.repository.MedecinRepository;
import com.example.api_medecin.repository.PatientRepository;

@Service
public class AuthService {
    // voir sil'on peut fusionner les trois repositories en un seul
    // ou si on peut utiliser un UserDetailsService pour gérer les utilisateurs

    private final MedecinRepository medecinRepository;
    private final PatientRepository patientRepository;
    private final CabinetRepository cabinetRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtEncoder jwtEncoder;

    public AuthService(JwtEncoder jwtEncoder, MedecinRepository medecinRepository, PatientRepository patientRepository, CabinetRepository cabinetRepository, PasswordEncoder passwordEncoder) {
        this.medecinRepository = medecinRepository;
        this.patientRepository = patientRepository;
        this.cabinetRepository = cabinetRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }
    
    // Example method for user login
    public String login(String email, String password) {
        // Logic to authenticate user
        return "User authenticated successfully";
    }

    // Example method for user registration
    public AuthenticatorResponse register(RequestBody request) {
        // Vérifier si email déjà pris
        if (medecinRepository.existsByEmail(request) ||
            patientRepository.existsByEmail(request) ||
            cabinetRepository.existsByEmail(request)) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        // // Créer utilisateur
        // User user = new User();
        // user.setEmail(request.getEmail());
        // user.setPassword(passwordEncoder.encode(request.getPassword()));
        // user.setRole("USER");

        // userRepository.save(user);

        // // Générer token JWT
        // String token = jwtService.generateToken(user);

        return new AuthenticatorResponse(token);
    }

        public String generateToken(String username) {
        // Claims du token
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(username)                   // identifiant utilisateur
                .issuedAt(Instant.now())             // date d'émission
                .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS)) // expiration
                .claim("role", "USER")               // rôle ou autre info
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}

package com.example.api_medecin.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.api_medecin.model.AuthResponse;
import com.example.api_medecin.model.Cabinet;
import com.example.api_medecin.model.Medecin;
import com.example.api_medecin.model.Patient;
import com.example.api_medecin.model.User;
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
    // AuthenticatorResponse
    public AuthResponse register(@RequestBody User request) {
        // Vérifier si email déjà pris
        if (medecinRepository.existsByEmail(request.getEmail()) ||
            patientRepository.existsByEmail(request.getEmail()) ||
            cabinetRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        User savedUser;
        String token;
        // Créer utilisateur
        if (request instanceof Medecin medecin) {
            medecin.setPassword(passwordEncoder.encode(medecin.getPassword()));
            medecin.setRole(request.getRole());
            savedUser = medecinRepository.save(medecin);
            token = generateToken(savedUser.getEmail(), savedUser.getRole().getName());
        } else if (request instanceof Patient patient) {
            patient.setPassword(passwordEncoder.encode(patient.getPassword()));
            patient.setRole(request.getRole());
            savedUser = patientRepository.save(patient);
            token = generateToken(savedUser.getEmail(), savedUser.getRole().getName());
        } else if (request instanceof Cabinet cabinet) {
            cabinet.setPassword(passwordEncoder.encode(cabinet.getPassword()));
            cabinet.setRole(request.getRole());
            savedUser = cabinetRepository.save(cabinet);
            token = generateToken(savedUser.getEmail(), savedUser.getRole().getName());
        } else {
            throw new RuntimeException("Type d'utilisateur inconnu !");
        }

        // Générer token JWT

    // 4️⃣ Retourner le token dans la réponse
        return new AuthResponse(token);
    }

    public String generateToken(String username, String role) {
        // Claims du token
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(username)                   // identifiant utilisateur
                .issuedAt(Instant.now())             // date d'émission
                .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS)) // expiration
                .claim("role", role)               // rôle ou autre info
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}

package com.example.api_medecin.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.api_medecin.dto.request.LoginRequest;
import com.example.api_medecin.dto.request.PatientRegisterRequest;
import com.example.api_medecin.dto.response.AuthResponse;
import com.example.api_medecin.model.Medecin;
import com.example.api_medecin.model.Patient;
import com.example.api_medecin.model.Role;
import com.example.api_medecin.model.User;

import com.example.api_medecin.repository.MedecinRepository;
import com.example.api_medecin.repository.PatientRepository;
import com.example.api_medecin.repository.RoleRepository;
import com.example.api_medecin.repository.UserRepository;

@Service
public class AuthService {
    // voir sil'on peut fusionner les trois repositories en un seul
    // ou si on peut utiliser un UserDetailsService pour gérer les utilisateurs
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtEncoder jwtEncoder;

    public AuthService(PatientRepository patientRepository,MedecinRepository medecinRepository ,RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, JwtEncoder jwtEncoder) {
        this.patientRepository = patientRepository;
        this.medecinRepository = medecinRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }
    
    // Example method for user login
    public AuthResponse login(LoginRequest request) {
        // Logic to authenticate user
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new RuntimeException("Invalid email or password");
        }
        boolean passwordCorrect = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!passwordCorrect) {
            throw new RuntimeException("Invalid email or password");
        }
        String token;
        String refreshToken;
        if (user instanceof Medecin) {
            Medecin medecin = (Medecin) user;
            // Additional logic for Medecin if needed
            token = generateToken(medecin.getMedecin_id(), user.getEmail(), user.getRole());
            refreshToken = generateRefreshToken(medecin.getMedecin_id());
        } else if (user instanceof Patient) {
            Patient patient = (Patient) user;
            token = generateToken(patient.getPatient_id(), user.getEmail(), user.getRole());
            refreshToken = generateRefreshToken(patient.getPatient_id());
            // Additional logic for Patient if needed
            
        } else {
            throw new RuntimeException("Unknown user type");
        }

        return new AuthResponse(token, refreshToken ,user.getEmail(), user.getRole().getName());

    }

    // AuthenticatorResponse
    public AuthResponse registerPatient(@RequestBody PatientRegisterRequest user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Patient patient = new Patient();
        patient.setNom(user.getNom());
        patient.setPrenom(user.getPrenom());
        patient.setEmail(user.getEmail());
        patient.setTelephone(user.getTelephone());
        patient.setDateNaissance(user.getDateDeNaissance());
        patient.setSexe(user.getSexe());
        patient.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepository.findByName("PATIENT");
        if (role == null) {
            throw new RuntimeException("Role not found");
        }
        patient.setRole(role);

        patientRepository.save(patient);

        String token = generateToken(patient.getPatient_id(), patient.getEmail(), role);
        String refreshToken = generateRefreshToken(patient.getPatient_id());

        return new AuthResponse(token, refreshToken, patient.getEmail(), role.getName());

    }

    public String generateToken(Long userId,String username, Role role) {
        // Claims du token
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(username)                   // identifiant utilisateur
                .issuedAt(Instant.now())             // date d'émission
                .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS)) // expiration
                .claim("role", role.getName())  
                .claim("userId", userId)             // rôle ou autre info
                .build();
        
            var headers = org.springframework.security.oauth2.jwt.JwsHeader.with(
                    org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256
            ).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(headers, claims)).getTokenValue();
    }

    public String generateRefreshToken(Long userId) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(userId.toString())                     // identifiant utilisateur
                .issuedAt(Instant.now())                        // date d'émission
                .expiresAt(Instant.now().plus(30, ChronoUnit.DAYS)) // expiration plus longue
                .build();

        var headers = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(headers, claims)).getTokenValue();
    }
}
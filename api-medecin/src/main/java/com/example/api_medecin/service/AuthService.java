package com.example.api_medecin.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.api_medecin.model.Cabinet;
import com.example.api_medecin.model.Medecin;
import com.example.api_medecin.model.Patient;
import com.example.api_medecin.model.Role;
import com.example.api_medecin.model.User;
import com.example.api_medecin.repository.CabinetRepository;
import com.example.api_medecin.repository.MedecinRepository;
import com.example.api_medecin.repository.PatientRepository;
import com.example.api_medecin.repository.RoleRepository;

@Service
public class AuthService {
    // voir sil'on peut fusionner les trois repositories en un seul
    // ou si on peut utiliser un UserDetailsService pour gérer les utilisateurs

    private final MedecinRepository medecinRepository;
    private final PatientRepository patientRepository;
    private final CabinetRepository cabinetRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtEncoder jwtEncoder;

    public AuthService(JwtEncoder jwtEncoder, MedecinRepository medecinRepository, PatientRepository patientRepository, CabinetRepository cabinetRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.medecinRepository = medecinRepository;
        this.patientRepository = patientRepository;
        this.cabinetRepository = cabinetRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }
    
    // Example method for user login
    public String login(String email, String password) {
        // Logic to authenticate user
        return "User authenticated successfully";
    }

    // AuthenticatorResponse
    public User register(@RequestBody User user) {
        User savedUser;
        Role roleUser;
        // Créer utilisateur
        if (user instanceof Medecin medecin) {
            medecin.setPassword(passwordEncoder.encode(medecin.getPassword()));
            roleUser = roleRepository.findByName("MEDECIN").stream().findFirst().orElseThrow(() -> new RuntimeException("Role MEDECIN not found"));
            medecin.setRole(roleUser);
            savedUser = medecinRepository.save(medecin);
        } else if (user instanceof Patient patient) {
            patient.setPassword(passwordEncoder.encode(patient.getPassword()));
            roleUser = roleRepository.findByName("PATIENT").stream().findFirst().orElseThrow(() -> new RuntimeException("Role PATIENT not found"));
            patient.setRole(roleUser);
            savedUser = patientRepository.save(patient);
        } else if (user instanceof Cabinet cabinet) {
            cabinet.setPassword(passwordEncoder.encode(cabinet.getPassword()));
            roleUser = roleRepository.findByName("CABINET").stream().findFirst().orElseThrow(() -> new RuntimeException("Role CABINET not found"));
            cabinet.setRole(roleUser);
            savedUser = cabinetRepository.save(cabinet);
        } else {
            throw new RuntimeException("Type d'utilisateur inconnu !");
        }
        return savedUser;

    }

    public String generateToken(Long userId,String username, Role role) {
        // Claims du token
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(username)                   // identifiant utilisateur
                .issuedAt(Instant.now())             // date d'émission
                .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS)) // expiration
                .claim("role", role.getName())               // rôle ou autre info
                .build();
        
            var headers = org.springframework.security.oauth2.jwt.JwsHeader.with(
                    org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256
            ).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(headers, claims)).getTokenValue();
    }
}

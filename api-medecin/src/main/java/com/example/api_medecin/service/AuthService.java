package com.example.api_medecin.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import com.example.api_medecin.model.Validation;
import com.example.api_medecin.repository.MedecinRepository;
import com.example.api_medecin.repository.PatientRepository;
import com.example.api_medecin.repository.RoleRepository;
import com.example.api_medecin.repository.UserRepository;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService{
    // voir sil'on peut fusionner les trois repositories en un seul
    // ou si on peut utiliser un UserDetailsService pour gérer les utilisateurs
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final JwtEncoder jwtEncoder;
    private ValidationService validationService;

    public void inscription(User utilisateur){

        if(!utilisateur.getEmail().contains("@")) {
            throw  new RuntimeException("Votre mail invalide");
        }
        if(!utilisateur.getEmail().contains(".")) {
            throw  new RuntimeException("Votre mail invalide");
        }

        Optional<User> utilisateurOptional = this.userRepository.findByEmail(utilisateur.getEmail());
        if(utilisateurOptional.isPresent()) {
            throw  new RuntimeException("Votre mail est déjà utilisé");
        }
        String mdpCrypte = this.passwordEncoder.encode(utilisateur.getPassword());
        utilisateur.setPassword(mdpCrypte);

        Role roleUtilisateur = new Role();
        if (utilisateur instanceof Patient) {
            roleUtilisateur.setName(TypeDeRole.PATIENT);
            utilisateur.setRole(roleUtilisateur);
            utilisateur = this.patientRepository.save((Patient) utilisateur);
        }else if (utilisateur instanceof Medecin) {
            roleUtilisateur.setName(TypeDeRole.MEDECIN);
            utilisateur.setRole(roleUtilisateur);
            utilisateur = this.medecinRepository.save((Medecin) utilisateur);
        }
        this.validationService.enregistrer(utilisateur);

    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByEmail(username)
                .orElseThrow(() -> new  UsernameNotFoundException("Aucun utilisateur ne corespond à cet identifiant"));
    }

    public void activation(Map<String, String> activation) {
        Validation validation = this.validationService.lireEnFonctionDuCode(activation.get("code"));
        if(Instant.now().isAfter(validation.getExpiration())){
            throw  new RuntimeException("Votre code a expiré");
        }
        User utilisateurActiver = this.userRepository.findById(validation.getUtilisateur().getId()).orElseThrow(() -> new RuntimeException("Utilisateur inconnu"));
        utilisateurActiver.setActif(true);
        this.userRepository.save(utilisateurActiver);
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
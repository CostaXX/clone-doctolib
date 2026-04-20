package com.example.api_medecin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_medecin.dto.request.AuthentificationDTO;
import com.example.api_medecin.dto.response.AuthResponse;
import com.example.api_medecin.model.Medecin;
import com.example.api_medecin.model.Patient;
import com.example.api_medecin.model.User;
import com.example.api_medecin.repository.UserRepository;
import com.example.api_medecin.service.AuthService;
import com.example.api_medecin.service.JwtService;
import com.example.api_medecin.service.ValidationService;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService utilisateurService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ValidationService validationService;
    private final UserRepository userRepository;

        @PostMapping(path = "connexion")
        public AuthResponse connexion(@RequestBody AuthentificationDTO authentificationDTO) {        
            try {

                final Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
                );

                if(authenticate.isAuthenticated()) {
                    Object user = userRepository.findUserInfoByEmail(authentificationDTO.username());
                    ResponseCookie cookieToken = ResponseCookie.from("token", jwtService.generate(authentificationDTO.username()).get(JwtService.BEARER))
                        .httpOnly(true)
                        .path("/")
                        .build();
                    ResponseCookie cookieRefreshToken = ResponseCookie.from("refreshToken", jwtService.generate(authentificationDTO.username()).get(JwtService.REFRESH))
                        .httpOnly(true)
                        .path("/")
                        .build();
                    
                    return ResponseEntity.ok()
                        .header("Set-Cookie", cookieToken.toString())
                        .header("Set-Cookie", cookieRefreshToken.toString())
                        .body(new AuthResponse(user))
                        .getBody();
                }
            } catch (LockedException e) {
                User user = userRepository.findByEmail(authentificationDTO.username()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
                validationService.supprimer(user);
                validationService.enregistrer(user);
            }

            
            
            return null;
        }

    @PostMapping(path = "deconnexion")
    public void deconnexion() {
        this.jwtService.deconnexion();
    }

    @PostMapping(path = "inscription")
    public void inscription(@RequestBody User utilisateur) {
    if (utilisateur instanceof Medecin) {
        Medecin medecin = (Medecin) utilisateur;
        this.utilisateurService.inscription(medecin);

    } else if (utilisateur instanceof Patient) {
        Patient patient = (Patient) utilisateur;
        this.utilisateurService.inscription(patient);

    }
        // log.info("Inscription");
        
    }

    @GetMapping("helloWorld")
    public String getMethodName(@RequestParam Map<String, String> param) {
        return new String();
    }
    

    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String, String> activation) {
        this.utilisateurService.activation(activation);
    }
    

    @PostMapping(path = "refresh-token")
    public @ResponseBody Map<String, String> refreshToken(@RequestBody Map<String, String> refreshTokenRequest) {
        return this.jwtService.refreshToken(refreshTokenRequest);
    }

    
    
}

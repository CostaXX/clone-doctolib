package com.example.api_medecin.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.api_medecin.model.User;
import com.example.api_medecin.model.Validation;
import com.example.api_medecin.repository.ValidationRepository;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@AllArgsConstructor
@Service
@Transactional
public class ValidationService {

    private ValidationRepository validationRepository;
    private NotificationService notificationService;

    public void enregistrer(User utilisateur){
        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);
        Instant creation = Instant.now();
        validation.setCreation(creation);
        Instant expiration = creation.plus(1, MINUTES);
        validation.setExpiration(expiration);
        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);

        validation.setCode(code);
        this.validationRepository.save(validation);
        this.notificationService.envoyer(validation);
    }

    public void supprimer(User utilisateur){
        this.validationRepository.deleteByUtilisateur(utilisateur);
    }

    public Validation lireEnFonctionDuCode(String code) {
        return this.validationRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Votre code est invalide"));
    }
}
package com.example.api_medecin.service;

import lombok.AllArgsConstructor;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.api_medecin.model.Validation;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@AllArgsConstructor
@Service
public class NotificationService {
    JavaMailSender javaMailSender;
    public void envoyer(Validation validation) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("no-reply@chillo.tech");
            helper.setTo(validation.getUtilisateur().getEmail());
            helper.setSubject("Votre code d'activation");

            String texte = String.format(
                    "Bonjour %s, <br /> Votre code d'action est %s; A bientôt",
                    validation.getUtilisateur().getNom(),
                    validation.getCode()
                    );
            helper.setText(texte, true);

        javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi du mail de validation", e);
        }
    }
}
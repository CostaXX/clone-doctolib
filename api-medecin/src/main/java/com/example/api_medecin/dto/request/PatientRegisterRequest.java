package com.example.api_medecin.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PatientRegisterRequest {

    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+$", message = "Le nom ne doit contenir que des lettres")
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+$", message = "Le prénom ne doit contenir que des lettres")
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @Pattern(regexp = "\\d{10}", message = "Le numéro de téléphone doit contenir exactement 10 chiffres")
    @NotBlank(message = "Le téléphone est obligatoire")
    private String telephone;

    @Email(message = "Adresse e-mail invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;


    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;

    @NotBlank(message = "La date de naissance est obligatoire")
    private LocalDate dateDeNaissance;

    @NotBlank(message = "Le sexe est obligatoire")
    private Integer sexe;

}

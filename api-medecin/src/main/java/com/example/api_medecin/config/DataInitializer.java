package com.example.api_medecin.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.api_medecin.model.Medecin;
import com.example.api_medecin.model.RendezVous;
import com.example.api_medecin.model.Role;
import com.example.api_medecin.repository.MedecinRepository;
import com.example.api_medecin.repository.RendezVousRepository;
import com.example.api_medecin.repository.RoleRepository;
import com.example.api_medecin.service.TypeDeRole;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final  RendezVousRepository rendezVousRepository;
    private final MedecinRepository medecinRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, RendezVousRepository rendezVousRepository, MedecinRepository medecinRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.rendezVousRepository = rendezVousRepository;
        this.medecinRepository = medecinRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if(roleRepository.findByName(TypeDeRole.PATIENT).isEmpty()) {
            Role rolePatient = new Role();
            rolePatient.setName(TypeDeRole.PATIENT);
            roleRepository.save(rolePatient);
        }
        if(roleRepository.findByName(TypeDeRole.MEDECIN).isEmpty()) {
            Role roleMedecin = new Role();
            roleMedecin.setName(TypeDeRole.MEDECIN);
            roleRepository.save(roleMedecin);
        }
        Medecin medecin = new Medecin();
        medecin.setNom("Dupont");
        medecin.setPrenom("Jean");
        medecin.setTelephone("0123456789");
        medecin.setEmail("qsdfghjikl@gmail.com");
        medecin.setPassword(this.passwordEncoder.encode("password"));
        medecin.setSpecialite("Cardiologue");
        medecin.setRpps("12345678901");

        medecinRepository.save(medecin);

        RendezVous rdv = new RendezVous();
        rdv.setMedecin(medecin);
        LocalDateTime heurDateTime = LocalDateTime.of(2024, 6, 15, 10, 0);
        rdv.setDateHeure(heurDateTime);
        rendezVousRepository.save(rdv);

        RendezVous rdv1 = new RendezVous();
        rdv1.setMedecin(medecin);
        LocalDateTime heurDateTime1 = LocalDateTime.of(2024, 6, 15, 11, 0);
        rdv1.setDateHeure(heurDateTime1);
        rendezVousRepository.save(rdv1);

        RendezVous rdv2 = new RendezVous();
        rdv2.setMedecin(medecin);
        LocalDateTime heurDateTime2 = LocalDateTime.of(2024, 6, 15, 11, 0);
        rdv2.setDateHeure(heurDateTime2);
        rendezVousRepository.save(rdv2);

    }
}
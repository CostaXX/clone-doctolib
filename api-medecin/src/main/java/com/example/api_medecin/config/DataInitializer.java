package com.example.api_medecin.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.api_medecin.model.Role;
import com.example.api_medecin.repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        if(roleRepository.findByName("PATIENT").isEmpty()) {
            Role rolePatient = new Role();
            rolePatient.setName("PATIENT");
            roleRepository.save(rolePatient);
        }
        if(roleRepository.findByName("MEDECIN").isEmpty()) {
            Role roleMedecin = new Role();
            roleMedecin.setName("MEDECIN");
            roleRepository.save(roleMedecin);
        }
    }
}
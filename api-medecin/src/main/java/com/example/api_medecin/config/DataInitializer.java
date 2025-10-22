package com.example.api_medecin.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.api_medecin.model.Role;
import com.example.api_medecin.repository.RoleRepository;
import com.example.api_medecin.service.TypeDeRole;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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
    }
}
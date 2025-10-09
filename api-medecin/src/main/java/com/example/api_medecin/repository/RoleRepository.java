package com.example.api_medecin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_medecin.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    
}

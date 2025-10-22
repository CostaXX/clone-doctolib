package com.example.api_medecin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_medecin.model.Role;
import com.example.api_medecin.service.TypeDeRole;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    Optional<Role> findByName(TypeDeRole name);
}

package com.example.api_medecin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_medecin.model.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}

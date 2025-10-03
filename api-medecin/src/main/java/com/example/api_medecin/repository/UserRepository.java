package com.example.api_medecin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_medecin.model.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    
    boolean existsByEmail(String email);
    User findByEmail(String email);
}

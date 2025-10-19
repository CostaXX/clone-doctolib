package com.example.api_medecin.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.api_medecin.model.Validation;

import java.util.Optional;

public interface ValidationRepository extends CrudRepository<Validation, Integer> {

    Optional<Validation> findByCode(String code);
}

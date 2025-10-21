package com.example.api_medecin.repository;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.api_medecin.model.Jwt;

public interface JwtRepository extends CrudRepository<Jwt, Integer> {
    Optional<Jwt> findByValueAndDesactiveAndExpire(String value, boolean desactive, boolean expire);

    @Query("FROM Jwt j WHERE j.expire = :expire AND j.desactive = :desactive AND j.utilisateur.email = :email")
    Optional<Jwt> findUtilisateurValidToken(String email, boolean desactive, boolean expire);

    @Query("FROM Jwt j WHERE j.utilisateur.email = :email")
    Stream<Jwt> findUtilisateur(String email);

    @Query("FROM Jwt j WHERE j.refreshToken.value = :value")
    Optional<Jwt> findByRefreshToken(String value);

    void deleteAllByExpireAndDesactive(boolean expire, boolean desactive);
}

package com.example.api_medecin.config;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;



@Configuration
public class JwtConfig {

    private static final String SECRET_KEY = "mTCrVf6gqv1w6t5c9lQ0xJtS6o0q9Eo8eJr4jW9B1z8="; // 32+ caractères

    @Bean
    public JwtEncoder jwtEncoder() {
        byte[] secret = java.util.Base64.getDecoder().decode(SECRET_KEY);
        javax.crypto.SecretKey key =
                new javax.crypto.spec.SecretKeySpec(secret, "HmacSHA256");
        return new org.springframework.security.oauth2.jwt.NimbusJwtEncoder(
                new com.nimbusds.jose.jwk.source.ImmutableSecret<>(key)
        );
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }
}
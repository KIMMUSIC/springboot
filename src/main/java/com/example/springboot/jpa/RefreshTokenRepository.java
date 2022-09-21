package com.example.springboot.jpa;

import com.example.springboot.jpa.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByUserId(String userId);
    Optional<RefreshToken> findBytoken(String token);

}
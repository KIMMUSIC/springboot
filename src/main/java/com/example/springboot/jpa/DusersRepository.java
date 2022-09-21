package com.example.springboot.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DusersRepository extends JpaRepository<Dusers, Long> {
    public Dusers findByUserId(String UserId);
}
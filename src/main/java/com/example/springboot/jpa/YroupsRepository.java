package com.example.springboot.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YroupsRepository extends JpaRepository<Yroups, Long> {
    public Yroups findBygroupname(String groupname);
    public Yroups findBygroupid(String groupid);
}

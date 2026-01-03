package com.challenge.backend.Insignia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.backend.Insignia.entity.Insignia;

@Repository
public interface InsigniaRepository extends JpaRepository<Insignia, Integer> {
}
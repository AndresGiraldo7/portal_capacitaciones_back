package com.challenge.backend.modulo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.backend.modulo.entity.Modulo;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Integer> {
}
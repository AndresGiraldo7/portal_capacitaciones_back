package com.challenge.backend.ProgresoCurso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.backend.ProgresoCurso.entity.ProgresoCurso;
import com.challenge.backend.usuario.entity.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgresoCursoRepository extends JpaRepository<ProgresoCurso, Integer> {
    
    @Query("SELECT p FROM ProgresoCurso p WHERE p.usuario.id = :idUsuario")
    List<ProgresoCurso> findByUsuarioId(@Param("idUsuario") Integer idUsuario);
    
    @Query("SELECT p FROM ProgresoCurso p WHERE p.curso.idCurso = :idCurso")
    List<ProgresoCurso> findByCursoId(@Param("idCurso") Integer idCurso);
    
    @Query("SELECT p FROM ProgresoCurso p WHERE p.usuario.id = :idUsuario AND p.curso.idCurso = :idCurso")
    Optional<ProgresoCurso> findByUsuarioIdAndCursoId(
        @Param("idUsuario") Integer idUsuario, 
        @Param("idCurso") Integer idCurso
    );
}
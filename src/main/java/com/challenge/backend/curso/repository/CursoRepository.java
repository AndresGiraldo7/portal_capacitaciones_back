package com.challenge.backend.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.challenge.backend.curso.entity.Curso;
import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
    
    /**
     * Listar TODOS los cursos con módulo cargado
     */
    @Query("SELECT c FROM Curso c JOIN FETCH c.modulo")
    List<Curso> findAll();
    
    /**
     * Listar cursos por módulo con JOIN FETCH para cargar el módulo
     */
    @Query("SELECT c FROM Curso c JOIN FETCH c.modulo m WHERE m.idModulo = :idModulo AND c.activo = true")
    List<Curso> findByModuloIdAndActivoTrue(@Param("idModulo") Integer idModulo);
    
    /**
     * Listar todos los cursos activos con módulo cargado
     */
    @Query("SELECT c FROM Curso c JOIN FETCH c.modulo WHERE c.activo = true")
    List<Curso> findByActivoTrue();
    
    /**
     * Buscar curso por ID con módulo cargado
     */
    @Query("SELECT c FROM Curso c JOIN FETCH c.modulo WHERE c.idCurso = :idCurso")
    Optional<Curso> findByIdWithModulo(@Param("idCurso") Integer idCurso);
    
    /**
     * Verificar si existe un curso con el mismo título en el mismo módulo
     * Retorna true si existe, false si no existe
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Curso c " +
           "WHERE c.titulo = :titulo AND c.modulo.idModulo = :idModulo AND c.activo = true")
    boolean existsByTituloAndModuloAndActivo(@Param("titulo") String titulo, @Param("idModulo") Integer idModulo);
}
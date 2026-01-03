package com.challenge.backend.curso.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.challenge.backend.curso.entity.Curso;
import com.challenge.backend.curso.record.CursoDTO;
import com.challenge.backend.curso.repository.CursoRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CursoService {
    
    private final CursoRepository cursoRepository;
    
    @Transactional(readOnly = true)
    public List<CursoDTO> listarTodos() {
        return cursoRepository.findAll().stream()
                .map(curso -> {
                    // Forzar carga del módulo
                    if (curso.getModulo() != null) {
                        curso.getModulo().getNombre();
                    }
                    return new CursoDTO(curso);
                })
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<CursoDTO> listarCursosPorModulo(Integer idModulo) {
        List<Curso> cursos = cursoRepository.findByModuloIdAndActivoTrue(idModulo);
        
        return cursos.stream()
                .map(curso -> {
                    // DEBUG: Imprimir para ver qué está pasando
                    System.out.println("Curso ID: " + curso.getIdCurso());
                    System.out.println("Módulo: " + (curso.getModulo() != null ? curso.getModulo().getIdModulo() : "NULL"));
                    
                    // Forzar la carga del módulo si es LAZY
                    if (curso.getModulo() != null) {
                        curso.getModulo().getNombre(); // Esto fuerza la carga
                    }
                    
                    return new CursoDTO(curso);
                })
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public CursoDTO obtenerCursoPorId(Integer idCurso) {
        return cursoRepository.findById(idCurso)
                .map(curso -> {
                    // Forzar carga del módulo
                    if (curso.getModulo() != null) {
                        curso.getModulo().getNombre();
                    }
                    return new CursoDTO(curso);
                })
                .orElse(null);
    }
    
    @Transactional(readOnly = true)
    public List<CursoDTO> listarCursosActivos() {
        return cursoRepository.findByActivoTrue()
                .stream()
                .map(curso -> {
                    // Forzar carga del módulo
                    if (curso.getModulo() != null) {
                        curso.getModulo().getNombre();
                    }
                    return new CursoDTO(curso);
                })
                .collect(Collectors.toList());
    }
    
    public boolean existeCursoConTitulo(String titulo, Integer idModulo) {
        return cursoRepository.existsByTituloAndModuloAndActivo(titulo, idModulo);
    }
    
    @Transactional
    public Curso crearCurso(Curso curso) {
        // Validación adicional por si acaso
        if (existeCursoConTitulo(curso.getTitulo(), curso.getModulo().getIdModulo())) {
            throw new IllegalArgumentException("Ya existe un curso con ese título en este módulo");
        }
        return cursoRepository.save(curso);
    }
    
    @Transactional
    public Curso actualizarCurso(Integer idCurso, Curso cursoActualizado) {
        return cursoRepository.findById(idCurso)
                .map(curso -> {
                    curso.setTitulo(cursoActualizado.getTitulo());
                    curso.setDescripcion(cursoActualizado.getDescripcion());
                    curso.setUrlContenido(cursoActualizado.getUrlContenido());
                    curso.setActivo(cursoActualizado.isActivo());
                    if (cursoActualizado.getModulo() != null) {
                        curso.setModulo(cursoActualizado.getModulo());
                    }
                    return cursoRepository.save(curso);
                })
                .orElse(null);
    }
    
    @Transactional
    public boolean eliminarCurso(Integer idCurso) {
        return cursoRepository.findById(idCurso)
                .map(curso -> {
                    curso.setActivo(false);
                    cursoRepository.save(curso);
                    return true;
                })
                .orElse(false);
    }
}
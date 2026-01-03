package com.challenge.backend.curso.controller;

import com.challenge.backend.curso.entity.Curso;
import com.challenge.backend.curso.record.CursoDTO;
import com.challenge.backend.curso.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {
    
    private final CursoService cursoService;
    
    /**
     * Listar TODOS los cursos (activos e inactivos)
     * GET /api/cursos
     */
    @GetMapping
    public ResponseEntity<List<CursoDTO>> listarTodos() {
        List<CursoDTO> cursos = cursoService.listarTodos();
        return ResponseEntity.ok(cursos);
    }
    
    /**
     * Listar cursos por módulo
     * GET /api/cursos/modulo/{idModulo}
     */
    @GetMapping("/modulo/{idModulo}")
    public ResponseEntity<List<CursoDTO>> listarPorModulo(@PathVariable("idModulo") Integer idModulo) {
        System.out.println("=== DEBUG: Listando cursos del módulo " + idModulo + " ===");
        List<CursoDTO> cursos = cursoService.listarCursosPorModulo(idModulo);
        
        // DEBUG: Imprimir los DTOs generados
        cursos.forEach(dto -> {
            System.out.println("DTO - Curso ID: " + dto.getId() + 
                             ", Título: " + dto.getTitulo() + 
                             ", idModulo: " + dto.getIdModulo() +
                             ", nombreModulo: " + dto.getNombreModulo());
        });
        
        return ResponseEntity.ok(cursos);
    }
    
    /**
     * Obtener curso por ID
     * GET /api/cursos/{idCurso}
     */
    @GetMapping("/{idCurso}")
    public ResponseEntity<CursoDTO> obtenerPorId(@PathVariable("idCurso") Integer idCurso) {
        CursoDTO curso = cursoService.obtenerCursoPorId(idCurso);
        return curso != null ? ResponseEntity.ok(curso) : ResponseEntity.notFound().build();
    }
    
    /**
     * Listar todos los cursos activos
     * GET /api/cursos/activos
     */
    @GetMapping("/activos")
    public ResponseEntity<List<CursoDTO>> listarActivos() {
        return ResponseEntity.ok(cursoService.listarCursosActivos());
    }
    
    /**
     * Crear un nuevo curso
     * POST /api/cursos
     */
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Curso curso) {
        try {
            // Validar que no exista un curso con el mismo título en el mismo módulo
            if (cursoService.existeCursoConTitulo(curso.getTitulo(), curso.getModulo().getIdModulo())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un curso con ese título en este módulo");
            }
            
            Curso nuevoCurso = cursoService.crearCurso(curso);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCurso);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al crear el curso: " + e.getMessage());
        }
    }
    
    /**
     * Actualizar un curso existente
     * PUT /api/cursos/{idCurso}
     */
    @PutMapping("/{idCurso}")
    public ResponseEntity<Curso> actualizar(
            @PathVariable("idCurso") Integer idCurso, 
            @RequestBody Curso curso) {
        Curso cursoActualizado = cursoService.actualizarCurso(idCurso, curso);
        return cursoActualizado != null 
            ? ResponseEntity.ok(cursoActualizado) 
            : ResponseEntity.notFound().build();
    }
    
    /**
     * Eliminar (desactivar) un curso
     * DELETE /api/cursos/{idCurso}
     */
    @DeleteMapping("/{idCurso}")
    public ResponseEntity<Void> eliminar(@PathVariable("idCurso") Integer idCurso) {
        return cursoService.eliminarCurso(idCurso) 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.notFound().build();
    }
}
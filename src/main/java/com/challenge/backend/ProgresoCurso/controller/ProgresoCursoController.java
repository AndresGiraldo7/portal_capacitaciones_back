package com.challenge.backend.ProgresoCurso.controller;

import com.challenge.backend.ProgresoCurso.dto.CrearProgresoDTO;
import com.challenge.backend.ProgresoCurso.dto.ProgresoCursoDTO;
import com.challenge.backend.ProgresoCurso.entity.ProgresoCurso;
import com.challenge.backend.ProgresoCurso.service.ProgresoCursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/progreso")
@RequiredArgsConstructor
public class ProgresoCursoController {

    private final ProgresoCursoService progresoCursoService;

    // Listar progresos por usuario - Retorna DTOs
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ProgresoCursoDTO>> listarPorUsuario(@PathVariable("idUsuario") Integer idUsuario) {
        List<ProgresoCursoDTO> progresos = progresoCursoService.listarProgresosPorUsuario(idUsuario);
        return ResponseEntity.ok(progresos);
    }

    // Obtener progreso específico
    @GetMapping("/{idProgreso}")
    public ResponseEntity<ProgresoCursoDTO> obtenerProgreso(@PathVariable Integer idProgreso) {
        ProgresoCursoDTO progreso = progresoCursoService.obtenerProgresoPorId(idProgreso);
        return progreso != null 
            ? ResponseEntity.ok(progreso) 
            : ResponseEntity.notFound().build();
    }

    // Obtener progreso de un usuario en un curso específico
    @GetMapping("/usuario/{idUsuario}/curso/{idCurso}")
    public ResponseEntity<ProgresoCursoDTO> obtenerProgresoPorUsuarioYCurso(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idCurso) {
        ProgresoCursoDTO progreso = progresoCursoService.obtenerProgresoPorUsuarioYCurso(idUsuario, idCurso);
        return progreso != null 
            ? ResponseEntity.ok(progreso) 
            : ResponseEntity.notFound().build();
    }

    // Crear/guardar progreso
    @PostMapping
    public ResponseEntity<?> guardarProgreso(@RequestBody CrearProgresoDTO dto) {
        try {
            ProgresoCurso nuevoProgreso = progresoCursoService.crearProgresoDesdeDTO(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProgreso);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al crear progreso: " + e.getMessage());
        }
    }

    // Actualizar progreso
    @PutMapping("/{idProgreso}")
    public ResponseEntity<ProgresoCurso> actualizarProgreso(
            @PathVariable Integer idProgreso,
            @RequestBody ProgresoCurso progreso) {
        ProgresoCurso progresoActualizado = progresoCursoService.actualizarProgreso(idProgreso, progreso);
        return progresoActualizado != null 
            ? ResponseEntity.ok(progresoActualizado) 
            : ResponseEntity.notFound().build();
    }

    // Marcar curso como completado
    @PatchMapping("/{idProgreso}/completar")
    public ResponseEntity<?> completarCurso(@PathVariable("idProgreso") Integer idProgreso)  {
        ProgresoCurso progresoCompletado = progresoCursoService.completarCurso(idProgreso);
        return progresoCompletado != null 
            ? ResponseEntity.ok(progresoCompletado) 
            : ResponseEntity.notFound().build();
    }

    // Eliminar progreso
    @DeleteMapping("/{idProgreso}")
    public ResponseEntity<Void> eliminarProgreso(@PathVariable Integer idProgreso) {
        return progresoCursoService.eliminarProgreso(idProgreso) 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.notFound().build();
    }
}
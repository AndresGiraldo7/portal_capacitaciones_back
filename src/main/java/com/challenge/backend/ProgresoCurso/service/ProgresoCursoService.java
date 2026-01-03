package com.challenge.backend.ProgresoCurso.service;

import com.challenge.backend.Insignia.entity.Insignia;
import com.challenge.backend.Insignia.repository.InsigniaRepository;
import com.challenge.backend.ProgresoCurso.dto.CrearProgresoDTO;
import com.challenge.backend.ProgresoCurso.dto.ProgresoCursoDTO;
import com.challenge.backend.ProgresoCurso.entity.ProgresoCurso;
import com.challenge.backend.ProgresoCurso.entity.ProgresoCurso.EstadoProgreso;
import com.challenge.backend.ProgresoCurso.repository.ProgresoCursoRepository;
import com.challenge.backend.UsuarioInsignia.entity.UsuarioInsignia;
import com.challenge.backend.UsuarioInsignia.service.UsuarioInsigniaService;
import com.challenge.backend.curso.entity.Curso;
import com.challenge.backend.curso.repository.CursoRepository;
import com.challenge.backend.usuario.entity.Usuario;
import com.challenge.backend.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgresoCursoService {
    
    private final ProgresoCursoRepository progresoCursoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final UsuarioInsigniaService usuarioInsigniaService;
    private final InsigniaRepository insigniaRepository;

    public List<ProgresoCursoDTO> listarProgresosPorUsuario(Integer idUsuario) {
        List<ProgresoCurso> progresos = progresoCursoRepository.findByUsuarioId(idUsuario);
        return progresos.stream()
                .map(ProgresoCursoDTO::new)
                .collect(Collectors.toList());
    }

    public ProgresoCursoDTO obtenerProgresoPorId(Integer idProgreso) {
        return progresoCursoRepository.findById(idProgreso)
                .map(ProgresoCursoDTO::new)
                .orElse(null);
    }

    public ProgresoCursoDTO obtenerProgresoPorUsuarioYCurso(Integer idUsuario, Integer idCurso) {
        return progresoCursoRepository.findByUsuarioIdAndCursoId(idUsuario, idCurso)
                .map(ProgresoCursoDTO::new)
                .orElse(null);
    }

    public ProgresoCurso crearProgresoDesdeDTO(CrearProgresoDTO dto) {
        Optional<ProgresoCurso> progresoExistente = progresoCursoRepository
                .findByUsuarioIdAndCursoId(dto.getIdUsuario(), dto.getIdCurso());
        
        if (progresoExistente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un progreso para este usuario y curso");
        }

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        Curso curso = cursoRepository.findById(dto.getIdCurso())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        ProgresoCurso progreso = ProgresoCurso.builder()
                .usuario(usuario)
                .curso(curso)
                .fechaInicio(LocalDateTime.now())
                .estado(EstadoProgreso.EN_PROGRESO)
                .build();

        ProgresoCurso progresoGuardado = progresoCursoRepository.save(progreso);
        
        // Verificar insignia "Iniciador Rápido" cuando se inicia un curso
        verificarInsigniaIniciadorRapido(usuario);
        
        return progresoGuardado;
    }

    public ProgresoCurso actualizarProgreso(Integer idProgreso, ProgresoCurso progresoActualizado) {
        return progresoCursoRepository.findById(idProgreso)
                .map(progreso -> {
                    if (progresoActualizado.getEstado() != null) {
                        progreso.setEstado(progresoActualizado.getEstado());
                    }
                    if (progresoActualizado.getFechaCompletado() != null) {
                        progreso.setFechaCompletado(progresoActualizado.getFechaCompletado());
                    }
                    return progresoCursoRepository.save(progreso);
                })
                .orElse(null);
    }

    public ProgresoCurso completarCurso(Integer idProgreso) {
        return progresoCursoRepository.findById(idProgreso)
                .map(progreso -> {
                    progreso.setEstado(EstadoProgreso.COMPLETADO);
                    progreso.setFechaCompletado(LocalDateTime.now());
                    ProgresoCurso progresoGuardado = progresoCursoRepository.save(progreso);
                    
                    try {
                        // Verificar todas las insignias relacionadas con completar cursos
                        verificarTodasLasInsignias(progreso.getUsuario());
                    } catch (Exception e) {
                        System.err.println("Error al verificar insignias: " + e.getMessage());
                        e.printStackTrace();
                    }
                    
                    return progresoGuardado;
                })
                .orElse(null);
    }

    public boolean eliminarProgreso(Integer idProgreso) {
        if (progresoCursoRepository.existsById(idProgreso)) {
            progresoCursoRepository.deleteById(idProgreso);
            return true;
        }
        return false;
    }

    /**
     * Verifica y otorga todas las insignias aplicables al usuario
     */
    private void verificarTodasLasInsignias(Usuario usuario) {
        // 1. Insignia: Curso Completado (ID: 1)
        verificarInsigniaCursoCompletado(usuario);
        
        // 2. Insignia: Aprendiz Constante (ID: 2) - 3 cursos completados
        verificarInsigniaAprendizConstante(usuario);
        
        // 3. Insignia: Maestro del Conocimiento (ID: 3) - 10 cursos completados
        verificarInsigniaMaestroConocimiento(usuario);
        
        // 5. Insignia: Perfeccionista (ID: 5) - Completa un módulo completo
        verificarInsigniaPerfeccionista(usuario);
    }

    /**
     * ID 1: Curso Completado - Completaste tu primer curso exitosamente
     */
    private void verificarInsigniaCursoCompletado(Usuario usuario) {
        long cursosCompletados = contarCursosCompletados(usuario);
        
        if (cursosCompletados >= 1) {
            otorgarInsignia(usuario, 1);
        }
    }

    /**
     * ID 2: Aprendiz Constante - Completa 3 cursos
     */
    private void verificarInsigniaAprendizConstante(Usuario usuario) {
        long cursosCompletados = contarCursosCompletados(usuario);
        
        if (cursosCompletados >= 3) {
            otorgarInsignia(usuario, 2);
        }
    }

    /**
     * ID 3: Maestro del Conocimiento - Completa 10 cursos
     */
    private void verificarInsigniaMaestroConocimiento(Usuario usuario) {
        long cursosCompletados = contarCursosCompletados(usuario);
        
        if (cursosCompletados >= 10) {
            otorgarInsignia(usuario, 3);
        }
    }

    /**
     * ID 4: Iniciador Rápido - Inicia 5 cursos en una semana
     */
    private void verificarInsigniaIniciadorRapido(Usuario usuario) {
        LocalDateTime hace7Dias = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
        
        List<ProgresoCurso> progresos = progresoCursoRepository.findByUsuarioId(usuario.getId());
        
        long cursosIniciadosEnSemana = progresos.stream()
                .filter(p -> p.getFechaInicio().isAfter(hace7Dias))
                .count();
        
        if (cursosIniciadosEnSemana >= 5) {
            otorgarInsignia(usuario, 4);
        }
    }

    /**
     * ID 5: Perfeccionista - Completa un módulo completo
     * (todos los cursos de un mismo módulo)
     */
    private void verificarInsigniaPerfeccionista(Usuario usuario) {
        List<ProgresoCurso> progresosCompletados = progresoCursoRepository
                .findByUsuarioId(usuario.getId())
                .stream()
                .filter(p -> p.getEstado() == EstadoProgreso.COMPLETADO)
                .collect(Collectors.toList());
        
        // Agrupar por módulo y verificar si completó todos los cursos de algún módulo
        var cursosPorModulo = progresosCompletados.stream()
                .collect(Collectors.groupingBy(p -> p.getCurso().getModulo().getIdModulo()));
        
        for (Integer moduloId : cursosPorModulo.keySet()) {
            List<ProgresoCurso> cursosDelModulo = cursosPorModulo.get(moduloId);
            
            // Obtener todos los cursos del módulo
            List<Curso> todosCursosDelModulo = cursoRepository.findAll()
                    .stream()
                    .filter(c -> c.getModulo().getIdModulo().equals(moduloId))
                    .collect(Collectors.toList());
            
            // Si completó todos los cursos del módulo, otorgar insignia
            if (cursosDelModulo.size() >= todosCursosDelModulo.size() && 
                todosCursosDelModulo.size() > 0) {
                otorgarInsignia(usuario, 5);
                break; // Solo necesita completar UN módulo completo
            }
        }
    }

    /**
     * Cuenta cuántos cursos ha completado un usuario
     */
    private long contarCursosCompletados(Usuario usuario) {
        return progresoCursoRepository.findByUsuarioId(usuario.getId())
                .stream()
                .filter(p -> p.getEstado() == EstadoProgreso.COMPLETADO)
                .count();
    }

    /**
     * Otorga una insignia al usuario si aún no la tiene
     */
    private void otorgarInsignia(Usuario usuario, Integer insigniaId) {
        try {
            Optional<Insignia> insigniaOpt = insigniaRepository.findById(insigniaId);
            
            if (insigniaOpt.isPresent()) {
                Insignia insignia = insigniaOpt.get();
                
                // Verificar si ya tiene esta insignia
                List<UsuarioInsignia> insigniasUsuario = usuarioInsigniaService.listarPorUsuario(usuario);
                boolean yaLaTiene = insigniasUsuario.stream()
                        .anyMatch(ui -> ui.getInsignia().getId().equals(insignia.getId()));
                
                if (!yaLaTiene) {
                    UsuarioInsignia usuarioInsignia = UsuarioInsignia.builder()
                            .usuario(usuario)
                            .insignia(insignia)
                            .fechaOtorgada(LocalDateTime.now())
                            .build();
                    
                    usuarioInsigniaService.otorgarInsignia(usuarioInsignia);
                    System.out.println("✅ Insignia otorgada: " + insignia.getNombre() + " a " + usuario.getNombre());
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error al otorgar insignia " + insigniaId + ": " + e.getMessage());
        }
    }
}
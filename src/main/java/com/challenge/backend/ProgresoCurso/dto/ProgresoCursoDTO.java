
package com.challenge.backend.ProgresoCurso.dto;

import com.challenge.backend.ProgresoCurso.entity.ProgresoCurso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgresoCursoDTO {
    private Integer idProgreso;
    private Integer idUsuario;
    private String nombreUsuario;
    private Integer idCurso;
    private String tituloCurso;
    private String descripcionCurso;
    private String urlContenido;
    private Integer idModulo;
    private String nombreModulo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaCompletado;
    private String estado;

    // Constructor desde entidad
    public ProgresoCursoDTO(ProgresoCurso progreso) {
        this.idProgreso = progreso.getIdProgreso();
        this.fechaInicio = progreso.getFechaInicio();
        this.fechaCompletado = progreso.getFechaCompletado();
        this.estado = progreso.getEstado() != null ? progreso.getEstado().toString() : null;
        
        // Datos del usuario
        if (progreso.getUsuario() != null) {
            this.idUsuario = progreso.getUsuario().getId();
            this.nombreUsuario = progreso.getUsuario().getNombre();
        }
        
        // Datos del curso
        if (progreso.getCurso() != null) {
            this.idCurso = progreso.getCurso().getIdCurso();
            this.tituloCurso = progreso.getCurso().getTitulo();
            this.descripcionCurso = progreso.getCurso().getDescripcion();
            this.urlContenido = progreso.getCurso().getUrlContenido();
            
            // Datos del módulo
            if (progreso.getCurso().getModulo() != null) {
                //this.idModulo = progreso.getCurso().getModulo().getIdCurso();
                this.nombreModulo = progreso.getCurso().getModulo().getNombre();
            }
        }
    }
}
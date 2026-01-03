package com.challenge.backend.curso.record;




import com.challenge.backend.curso.entity.Curso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private String urlContenido;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    
    // Datos del módulo
    private Integer idModulo;
    private String nombreModulo;
    private String descripcionModulo;
    
    // Constructor desde entidad
    public CursoDTO(Curso curso) {
        this.id = curso.getIdCurso();
        this.titulo = curso.getTitulo();
        this.descripcion = curso.getDescripcion();
        this.urlContenido = curso.getUrlContenido();
        this.activo = curso.isActivo();
        this.fechaCreacion = curso.getFechaCreacion();
        
        // IMPORTANTE: Mapear correctamente el módulo
        if (curso.getModulo() != null) {
            this.idModulo = curso.getModulo().getIdModulo();
            this.nombreModulo = curso.getModulo().getNombre();
            this.descripcionModulo = curso.getModulo().getDescripcion();
        }
    }
}
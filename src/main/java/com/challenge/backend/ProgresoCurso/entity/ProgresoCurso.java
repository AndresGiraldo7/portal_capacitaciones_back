package com.challenge.backend.ProgresoCurso.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.challenge.backend.curso.entity.Curso;
import com.challenge.backend.usuario.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "progreso_curso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"usuario", "curso"})
@ToString(exclude = {"usuario", "curso"})
public class ProgresoCurso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_progreso")
    private Integer idProgreso;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", nullable = false)
    @JsonIgnore
    private Curso curso;
    
    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;
    
    @Column(name = "fecha_completado")
    private LocalDateTime fechaCompletado;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20)
    private EstadoProgreso estado;
    
    public enum EstadoProgreso {
        NO_INICIADO,
        EN_PROGRESO,
        COMPLETADO
    }
    
    @PrePersist
    protected void onCreate() {
        this.fechaInicio = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = EstadoProgreso.NO_INICIADO;
        }
    }
}
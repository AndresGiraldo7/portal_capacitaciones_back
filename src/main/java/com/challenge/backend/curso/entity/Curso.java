package com.challenge.backend.curso.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import com.challenge.backend.ProgresoCurso.entity.ProgresoCurso;
import com.challenge.backend.modulo.entity.Modulo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "curso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"modulo", "progresos"})
@ToString(exclude = {"modulo", "progresos"})
public class Curso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Integer idCurso;
    
    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "url_contenido", length = 500)
    private String urlContenido;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "activo")
    private boolean activo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_modulo", nullable = false)
    private Modulo modulo;
    
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  // Evita serialización de la colección
    @Builder.Default
    private List<ProgresoCurso> progresos = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.activo == false) {
            this.activo = true;
        }
    }
}
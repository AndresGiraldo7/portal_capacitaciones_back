package com.challenge.backend.modulo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import com.challenge.backend.curso.entity.Curso;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "modulo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "cursos")
@ToString(exclude = "cursos")
public class Modulo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modulo")
    private Integer idModulo;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  // Evita serialización de la colección
    @Builder.Default
    private List<Curso> cursos = new ArrayList<>();
}

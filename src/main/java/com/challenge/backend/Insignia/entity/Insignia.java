package com.challenge.backend.Insignia.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

import com.challenge.backend.UsuarioInsignia.entity.UsuarioInsignia;

@Entity
@Table(name = "insignia", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nombre")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Insignia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_insignia")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "imagen_url", length = 255)
    private String imagenUrl;

    @OneToMany(mappedBy = "insignia", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuarioInsignia> usuarios;
}
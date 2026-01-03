package com.challenge.backend.usuario.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

import com.challenge.backend.ProgresoCurso.entity.ProgresoCurso;
import com.challenge.backend.UsuarioInsignia.entity.UsuarioInsignia;

@Entity
@Table(name = "usuario", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "rol", nullable = false, length = 20)
    private String rol; // USER o ADMIN

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProgresoCurso> progresos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuarioInsignia> insignias;
}
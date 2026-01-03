package com.challenge.backend.UsuarioInsignia.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.challenge.backend.Insignia.entity.Insignia;
import com.challenge.backend.usuario.entity.Usuario;

@Entity
@Table(name = "usuario_insignia")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UsuarioInsigniaId.class)
public class UsuarioInsignia {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_insignia")
    private Insignia insignia;

    @Column(name = "fecha_otorgada", nullable = false)
    private LocalDateTime fechaOtorgada;
}

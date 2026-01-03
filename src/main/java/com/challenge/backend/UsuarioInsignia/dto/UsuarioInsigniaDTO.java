package com.challenge.backend.UsuarioInsignia.dto;

import com.challenge.backend.UsuarioInsignia.entity.UsuarioInsignia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioInsigniaDTO {
    private UsuarioDTO usuario;
    private InsigniaDTO insignia;
    private LocalDateTime fechaOtorgada;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsuarioDTO {
        private Integer id;
        private String nombre;
        private String username;
        private String email;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InsigniaDTO {
        private Integer id;
        private String nombre;
        private String descripcion;
        private String imagenUrl;
    }

    // Constructor desde entidad
    public UsuarioInsigniaDTO(UsuarioInsignia entity) {
        this.usuario = UsuarioDTO.builder()
                .id(entity.getUsuario().getId())
                .nombre(entity.getUsuario().getNombre())
                .username(entity.getUsuario().getUsername())
                .email(entity.getUsuario().getEmail())
                .build();
        
        this.insignia = InsigniaDTO.builder()
                .id(entity.getInsignia().getId())
                .nombre(entity.getInsignia().getNombre())
                .descripcion(entity.getInsignia().getDescripcion())
                .imagenUrl(entity.getInsignia().getImagenUrl())
                .build();
        
        this.fechaOtorgada = entity.getFechaOtorgada();
    }
}
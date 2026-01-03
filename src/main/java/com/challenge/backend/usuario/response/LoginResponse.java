package com.challenge.backend.usuario.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Integer id;
    private String username;
    private String nombre;
    private String email;
    private String rol;
}
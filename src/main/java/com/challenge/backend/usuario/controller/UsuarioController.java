package com.challenge.backend.usuario.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.challenge.backend.usuario.entity.Usuario;
import com.challenge.backend.usuario.requests.LoginRequest;
import com.challenge.backend.usuario.response.LoginResponse;
import com.challenge.backend.usuario.service.UsuarioService;


import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return usuarioService.autenticar(request.getUsername(), request.getPassword())
                .map(usuario -> ResponseEntity.ok(LoginResponse.builder()
                        .id(usuario.getId())
                        .username(usuario.getUsername())
                        .nombre(usuario.getNombre())
                        .email(usuario.getEmail())
                        .rol(usuario.getRol())
                        .build()))
                .orElse(ResponseEntity.status(401).build());
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") Integer id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.guardarUsuario(usuario));
    }
}
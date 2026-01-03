package com.challenge.backend.UsuarioInsignia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.challenge.backend.UsuarioInsignia.dto.UsuarioInsigniaDTO;
import com.challenge.backend.UsuarioInsignia.entity.UsuarioInsigniaId;
import com.challenge.backend.UsuarioInsignia.service.UsuarioInsigniaService;
import com.challenge.backend.usuario.service.UsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuario-insignias")
@RequiredArgsConstructor
public class UsuarioInsigniaController {
    
    private final UsuarioInsigniaService service;
    private final UsuarioService usuarioService;
    
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<UsuarioInsigniaDTO>> listarPorUsuario(
            @PathVariable("idUsuario") Integer idUsuario) {
        return usuarioService.buscarPorId(idUsuario)
                .map(usuario -> {
                    List<UsuarioInsigniaDTO> dtos = service.listarPorUsuario(usuario)
                            .stream()
                            .map(UsuarioInsigniaDTO::new)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(dtos);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping
    public ResponseEntity<Void> eliminar(
            @RequestParam("idUsuario") Integer idUsuario,
            @RequestParam("idInsignia") Integer idInsignia) {
        UsuarioInsigniaId id = new UsuarioInsigniaId();
        id.setUsuario(idUsuario);
        id.setInsignia(idInsignia);
        service.eliminarInsignia(id);
        return ResponseEntity.noContent().build();
    }
}
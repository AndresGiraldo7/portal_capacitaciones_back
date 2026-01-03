package com.challenge.backend.modulo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.challenge.backend.modulo.entity.Modulo;
import com.challenge.backend.modulo.service.ModuloService;

import java.util.List;

@RestController
@RequestMapping("/api/modulos")
@RequiredArgsConstructor
public class ModuloController {

    private final ModuloService moduloService;

    @GetMapping
    public ResponseEntity<List<Modulo>> listarModulos() {
        return ResponseEntity.ok(moduloService.listarModulos());
    }

    @PostMapping
    public ResponseEntity<Modulo> guardarModulo(@RequestBody Modulo modulo) {
        return ResponseEntity.ok(moduloService.guardarModulo(modulo));
    }
}
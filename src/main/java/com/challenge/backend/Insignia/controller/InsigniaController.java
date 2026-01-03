package com.challenge.backend.Insignia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.challenge.backend.Insignia.entity.Insignia;
import com.challenge.backend.Insignia.service.InsigniaService;

import java.util.List;

@RestController
@RequestMapping("/api/insignias")
@RequiredArgsConstructor
public class InsigniaController {

    private final InsigniaService insigniaService;

    @GetMapping
    public ResponseEntity<List<Insignia>> listarInsignias() {
        return ResponseEntity.ok(insigniaService.listarInsignias());
    }

    @PostMapping
    public ResponseEntity<Insignia> guardarInsignia(@RequestBody Insignia insignia) {
        return ResponseEntity.ok(insigniaService.guardarInsignia(insignia));
    }
}
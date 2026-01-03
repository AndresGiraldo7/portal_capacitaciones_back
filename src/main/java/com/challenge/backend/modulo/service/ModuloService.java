package com.challenge.backend.modulo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.challenge.backend.modulo.entity.Modulo;
import com.challenge.backend.modulo.repository.ModuloRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuloService {

    private final ModuloRepository moduloRepository;

    public List<Modulo> listarModulos() {
        return moduloRepository.findAll();
    }

    public Modulo guardarModulo(Modulo modulo) {
        return moduloRepository.save(modulo);
    }
}
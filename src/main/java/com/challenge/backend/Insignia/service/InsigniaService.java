package com.challenge.backend.Insignia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.challenge.backend.Insignia.entity.Insignia;
import com.challenge.backend.Insignia.repository.InsigniaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsigniaService {

    private final InsigniaRepository insigniaRepository;

    public List<Insignia> listarInsignias() {
        return insigniaRepository.findAll();
    }

    public Insignia guardarInsignia(Insignia insignia) {
        return insigniaRepository.save(insignia);
    }
}
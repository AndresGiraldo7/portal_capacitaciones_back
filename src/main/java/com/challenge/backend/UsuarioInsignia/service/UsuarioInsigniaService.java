package com.challenge.backend.UsuarioInsignia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.challenge.backend.UsuarioInsignia.entity.UsuarioInsignia;
import com.challenge.backend.UsuarioInsignia.entity.UsuarioInsigniaId;
import com.challenge.backend.UsuarioInsignia.repository.UsuarioInsigniaRepository;
import com.challenge.backend.usuario.entity.Usuario;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioInsigniaService {

    private final UsuarioInsigniaRepository repository;

    public List<UsuarioInsignia> listarPorUsuario(Usuario usuario) {
        return repository.findByUsuario(usuario);
    }

    public UsuarioInsignia otorgarInsignia(UsuarioInsignia ui) {
        return repository.save(ui);
    }

    public void eliminarInsignia(UsuarioInsigniaId id) {
        repository.deleteById(id);
    }
}
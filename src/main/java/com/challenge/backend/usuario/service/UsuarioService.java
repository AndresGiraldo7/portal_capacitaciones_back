package com.challenge.backend.usuario.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.challenge.backend.usuario.entity.Usuario;
import com.challenge.backend.usuario.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        usuario.setPasswordHash("{noop}" + usuario.getPasswordHash());
        usuario.setFechaCreacion(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }
    public void eliminarUsuario(Integer idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }
    
    public Optional<Usuario> autenticar(String username, String password) {
        return usuarioRepository.findByUsername(username)
                .filter(u -> u.getPasswordHash().equals(password));
    }
}

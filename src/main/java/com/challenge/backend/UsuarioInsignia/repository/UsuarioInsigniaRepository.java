package com.challenge.backend.UsuarioInsignia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.backend.UsuarioInsignia.entity.UsuarioInsignia;
import com.challenge.backend.UsuarioInsignia.entity.UsuarioInsigniaId;
import com.challenge.backend.usuario.entity.Usuario;

import java.util.List;

@Repository
public interface UsuarioInsigniaRepository extends JpaRepository<UsuarioInsignia, UsuarioInsigniaId> {

    List<UsuarioInsignia> findByUsuario(Usuario usuario);
}
package com.api.sistemachamados.service;

import com.api.sistemachamados.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UsuarioService {

    Page<Usuario> buscarTodos(Pageable pageable);

    Optional<Usuario> buscarPorId(Integer id);

    Optional<Usuario> salvar(Usuario usuario);

    void atualizar(Usuario usuario);

    Optional<Usuario> buscarPorEmail(String email);

    void deletar(Usuario usuario);
}

package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.UsuarioDTO;
import com.api.sistemachamados.entity.Usuario;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UsuarioService {

    Page<Usuario> buscarTodos(Pageable pageable);

    Optional<Usuario> buscarPorId(Long id) throws NotFoundException;

    Optional<Usuario> salvar(UsuarioDTO usuario);


    Optional<Usuario> buscarPorEmail(String email) throws NotFoundException;

    void deletar(Usuario usuario);

    Usuario verificaPersitencia(UsuarioDTO usuarioDTO);

    void atualizandoAtributosCliente(Usuario usuarioBD, Usuario usuario, UsuarioDTO usuarioDTO);
}

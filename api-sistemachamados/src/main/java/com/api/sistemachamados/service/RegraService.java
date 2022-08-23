package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.RegraDTO;
import com.api.sistemachamados.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RegraService {

    Page<Role> buscarTodos(Pageable pageable);

    Optional<Role> buscarPorId(Long id);

    Optional<Role> buscarPorNome(String nome);

    Optional<Role> salvar(RegraDTO role);

    void deletar(Role role);
}

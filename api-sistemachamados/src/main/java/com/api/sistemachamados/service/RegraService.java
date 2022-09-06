package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.ClienteDTO;
import com.api.sistemachamados.dto.RegraDTO;
import com.api.sistemachamados.dto.RoleDTO;
import com.api.sistemachamados.entity.Cliente;
import com.api.sistemachamados.entity.Role;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RegraService {

    Page<Role> buscarTodos(Pageable pageable);

    Optional<Role> buscarPorId(Long id) throws NotFoundException;

    Optional<Role> buscarPorNome(String nome) throws NotFoundException;

    Optional<Role> salvar(RegraDTO role);

    void deletar(Role role);

    Role verificaPersitencia(RegraDTO roleDTO);

    void atualizandoAtributosCliente(Role roleBD, Role role);
}

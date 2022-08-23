package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.ClienteDTO;
import com.api.sistemachamados.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClienteService {

    Page<Cliente> buscarTodos(Pageable pageable);

    Optional<Cliente> buscarPorId(Long id);

    Optional<Cliente> salvar(ClienteDTO cliente);

    Optional<Cliente> buscarClienteCpfCnpj(String documento);

    void deletar(Cliente cliente);
}

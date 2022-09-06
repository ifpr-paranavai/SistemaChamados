package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.ClienteDTO;
import com.api.sistemachamados.entity.Cliente;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClienteService {

    Page<Cliente> buscarTodos(Pageable pageable);

    Optional<Cliente> buscarPorId(Long id) throws NotFoundException;

    Optional<Cliente> salvar(ClienteDTO cliente);

    Optional<Cliente> buscarClienteCpfCnpj(String documento) throws NotFoundException;

    void deletar(Cliente cliente);

    Cliente verificaPersitencia(ClienteDTO clienteDTO);

    void atualizandoAtributosCliente(Cliente clienteBD, Cliente cliente);
}

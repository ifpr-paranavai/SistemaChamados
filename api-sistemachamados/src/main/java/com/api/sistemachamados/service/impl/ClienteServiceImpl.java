package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.ClienteDTO;
import com.api.sistemachamados.entity.Cliente;
import com.api.sistemachamados.repository.ClienteRepository;
import com.api.sistemachamados.service.ClienteService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.api.sistemachamados.utils.Utils.copiarAtributosIgnorandoNullos;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteServiceImpl.class);

    final ClienteRepository clienteRepository;


    @Override
    public Page<Cliente> buscarTodos(Pageable pageable) {
        LOGGER.info("Buscando Todas Clientes");
        return clienteRepository.findAll(pageable);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) throws NotFoundException {
        LOGGER.info("Buscando Cliente pelo ID: {}", id);
        return Optional.ofNullable(clienteRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("cliente.naoEncontrado")));
    }


    @Override
    @Transactional
    public Optional<Cliente> salvar(ClienteDTO clienteDTO) {
        try {
            return Optional.of(clienteRepository.save(verificaPersitencia(clienteDTO)));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public Cliente verificaPersitencia(ClienteDTO clienteDTO) {
        try {
            var cliente = new Cliente();
            LOGGER.info("Buscando se existe Cliente");
            clienteRepository.findByCpfCnpj(clienteDTO.getCpfCnpj()).ifPresentOrElse
                (value ->
                    {
                        throw new com.api.sistemachamados.exception.DataIntegrityViolationException
                            ("Já existe um Cliente com esse CPF/CNPJ");
                    },
                    () -> BeanUtils.copyProperties(clienteDTO, cliente));
            return cliente;
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    public Cliente verificaPersitenciaAtualizar(ClienteDTO clienteDTO) {
        try {
            var cliente = new Cliente();
            LOGGER.info("Buscando se existe Cliente");
            clienteRepository.findByCpfCnpj(clienteDTO.getCpfCnpj()).ifPresentOrElse
                (value ->
                    {
                        copiarAtributosIgnorandoNullos(clienteDTO, cliente);
                        atualizandoAtributosCliente(
                            Objects.requireNonNull(value), cliente);
                    },
                    () -> BeanUtils.copyProperties(clienteDTO, cliente));
            return cliente;
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public void atualizandoAtributosCliente(Cliente clienteBD, Cliente cliente) {
        cliente.setId(clienteBD.getId());
    }


    @Override
    public Optional<Cliente> buscarClienteCpfCnpj(String documento) throws NotFoundException {
        LOGGER.info("Buscando Cliente pelo nome: {}", documento);
        return Optional.ofNullable(clienteRepository.findByCpfCnpj(documento)
            .orElseThrow(() -> new NotFoundException("documento.naoEncontrado")));
    }

    @Override
    @Transactional
    public void deletar(Cliente cliente) {
        try {
            clienteRepository.delete(cliente);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.deleted.persist $e");
        }
    }
}


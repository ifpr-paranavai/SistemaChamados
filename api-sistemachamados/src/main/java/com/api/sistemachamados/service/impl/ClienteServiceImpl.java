package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.ClienteDTO;
import com.api.sistemachamados.entity.Cliente;
import com.api.sistemachamados.exception.BadRequestException;
import com.api.sistemachamados.repository.ClienteRepository;
import com.api.sistemachamados.service.ClienteService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Optional<Cliente> buscarPorId(Long id) {
        LOGGER.info("Buscando Cliente pelo ID: {}", id);
        return Optional.ofNullable(clienteRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("cliente.naoEncontrado")));
    }


    @Override
    @Transactional
    public Optional<Cliente> salvar(ClienteDTO clienteDTO) {
        try {
            LOGGER.info("Buscando se existe Cliente");
            var novaCliente = new Cliente();
            var cliente = clienteRepository.findByCpfCnpj(clienteDTO.getCpfCnpj());
            if (cliente.isEmpty()) {
                LOGGER.info("Salvando Cliente");
                BeanUtils.copyProperties(clienteDTO, novaCliente);
            } else {
                LOGGER.info("Atualizando Cliente");
                copiarAtributosIgnorandoNullos(clienteDTO, novaCliente);
                novaCliente.setId(cliente.get().getId());
            }
            novaCliente = clienteRepository.save(novaCliente);
            return Optional.of(novaCliente);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public Optional<Cliente> buscarClienteCpfCnpj(String documento) {
        LOGGER.info("Buscando Cliente pelo nome: {}", documento);
        return Optional.ofNullable(clienteRepository.findByCpfCnpj(documento)
            .orElseThrow(() -> new BadRequestException("documento.naoEncontrado")));
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


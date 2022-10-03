package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.entity.Estado;
import com.api.sistemachamados.repository.EstadoRepository;
import com.api.sistemachamados.service.EstadoService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EstadoServiceImpl implements EstadoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstadoServiceImpl.class);

    final EstadoRepository estadoRepository;

    @Override
    public Page<Estado> buscarTodos(Pageable pageable) {
        LOGGER.info("Buscando Todas Estados");
        return estadoRepository.findAll(pageable);
    }

    @Override
    public Optional<Estado> buscarPorId(Long id) throws NotFoundException {
        LOGGER.info("Buscando Estado pelo ID: {}", id);
        return Optional.ofNullable(estadoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("estado.naoEncontrado")));
    }
}


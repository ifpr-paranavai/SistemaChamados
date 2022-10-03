package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.entity.Cidade;
import com.api.sistemachamados.repository.CidadeRepository;
import com.api.sistemachamados.service.CidadeService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CidadeServiceImpl implements CidadeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CidadeServiceImpl.class);

    final CidadeRepository cidadeRepository;

    @Override
    public Page<Cidade> buscarTodos(Pageable pageable) {
        LOGGER.info("Buscando Todas Cidades");
        return cidadeRepository.findAll(pageable);
    }

    @Override
    public Optional<Cidade> buscarPorId(Long id) throws NotFoundException {
        LOGGER.info("Buscando Cidade pelo ID: {}", id);
        return Optional.ofNullable(cidadeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("cidade.naoEncontrado")));
    }

    @Override
    public List<Optional<Cidade>> buscarPorIdEstado(Long id) {
        LOGGER.info("Buscando Cidade pelo ID Estado: {}", id);
        return cidadeRepository.findByEstadoId(id);
    }

}


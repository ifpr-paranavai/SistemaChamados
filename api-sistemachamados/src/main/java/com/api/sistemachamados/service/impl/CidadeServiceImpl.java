package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.entity.Cidade;
import com.api.sistemachamados.repository.CidadeRepository;
import com.api.sistemachamados.service.CidadeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class CidadeServiceImpl implements CidadeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CidadeServiceImpl.class);

    final
    CidadeRepository cidadeRepository;

    @Override
    public Page<Cidade> findAll(Pageable pageable) {
        LOGGER.info("Buscando Todas Cidades");
        return cidadeRepository.findAll(pageable);
    }

    @Override
    public Optional<Cidade> findById(Long id) {
        LOGGER.info("Buscando Cidade pelo ID: {}", id);
        Optional<Cidade> cidade = cidadeRepository.findById(id);
        if (isNull(cidade)) {
            throw new RuntimeException("Pessoa não Encontrada");
        }
        return cidade;
    }

    @Override
    @Transactional
    public List<Cidade> saveAll(ArrayList<Cidade> cidades) {
        LOGGER.info("Salvando Lista de Cidades");
        return cidadeRepository.saveAll(cidades);
    }

    @Override
    @Transactional
    public Cidade save(Cidade cidadeDto) {
        try {
            LOGGER.info("Buscando se existe Cidade");
            var cidade = cidadeRepository.findByNome(cidadeDto.getNome());
            if (Objects.isNull(cidade)) {
                LOGGER.info("Salvando Cidade");
                cidade = cidadeRepository.save(cidadeDto);
            }
            return cidade;
        } catch (Exception e) {
            LOGGER.error(e.toString(), e);
            throw e;
        }
    }

    @Override
    public Cidade findByNomeCidade(String nomeCidade) {
        LOGGER.info("Buscando Cidade pelo nome: {}", nomeCidade);
        return cidadeRepository.findByNome(nomeCidade);
    }
}

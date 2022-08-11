package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.MarcaDTO;
import com.api.sistemachamados.entity.Marca;
import com.api.sistemachamados.entity.Marca;
import com.api.sistemachamados.exception.BadRequestException;
import com.api.sistemachamados.repository.MarcaRepository;
import com.api.sistemachamados.service.MarcaService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MarcaServiceImpl implements MarcaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarcaServiceImpl.class);

    final MarcaRepository marcaRepository;


    @Override
    public Page<Marca> buscarTodos(Pageable pageable) {
        LOGGER.info("Buscando Todas Marcas");
        return marcaRepository.findAll(pageable);
    }

    @Override
    public Optional<Marca> buscarPorId(Long id) {
        LOGGER.info("Buscando Marca pelo ID: {}", id);
        return Optional.ofNullable(marcaRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("Marca não Encontrada")));
    }

    @Override
    @Transactional
    public Optional<Marca> salvar(MarcaDTO marcaDTO) {
        try {
            LOGGER.info("Buscando se existe Marca");
            var novaMarca = new Marca();
            var marca = marcaRepository.findByNomeMarca(marcaDTO.getNomeMarca());
            if (marca.isEmpty()) {
                LOGGER.info("Salvando Marca");
                BeanUtils.copyProperties(marcaDTO, novaMarca);
                novaMarca.setDeleted(false);
            } else {
                LOGGER.info("Atualizando Marca");
                BeanUtils.copyProperties(marcaDTO, novaMarca);
                novaMarca.setId(marca.get().getId());
            }
            novaMarca = marcaRepository.save(novaMarca);
            return Optional.of(novaMarca);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("Ocorreu um erro ao salvar Objeto!!! " + e);
        }
    }

    @Override
    public Optional<Marca> buscarNomeMarca(String marca) {
        LOGGER.info("Buscando Marca pelo nome: {}", marca);
        return Optional.ofNullable(marcaRepository.findByNomeMarca(marca)
            .orElseThrow(() -> new BadRequestException("Marca não Encontrada")));
    }

    @Override
    @Transactional
    public void deletar(Marca marca) {
        try {
            marcaRepository.delete(marca);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("Ocorreu um erro ao apagar Objeto!!! " + e);
        }
    }
}


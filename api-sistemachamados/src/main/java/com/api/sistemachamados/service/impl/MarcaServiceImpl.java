package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.MarcaDTO;
import com.api.sistemachamados.entity.Cliente;
import com.api.sistemachamados.entity.Marca;
import com.api.sistemachamados.entity.Marca;
import com.api.sistemachamados.exception.BadRequestException;
import com.api.sistemachamados.repository.MarcaRepository;
import com.api.sistemachamados.service.MarcaService;
import javassist.NotFoundException;
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

import java.util.Objects;
import java.util.Optional;

import static com.api.sistemachamados.utils.Utils.copiarAtributosIgnorandoNullos;

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
    public Optional<Marca> buscarPorId(Long id) throws NotFoundException {
        LOGGER.info("Buscando Marca pelo ID: {}", id);
        return Optional.ofNullable(marcaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("marca.naoEncontrado")));
    }

    @Override
    @Transactional
    public Optional<Marca> salvar(MarcaDTO marcaDTO) {
        try {
            return Optional.of(marcaRepository.save(verificaPersitencia(marcaDTO)));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("Ocorreu um erro ao salvar Objeto!!! " + e);
        }
    }

    @Override
    public Optional<Marca> buscarNomeMarca(String marca) throws NotFoundException {
        LOGGER.info("Buscando Marca pelo nome: {}", marca);
        return Optional.ofNullable(marcaRepository.findByNomeMarca(marca)
            .orElseThrow(() -> new NotFoundException("marca.naoEncontrado")));
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

    @Override
    public Marca verificaPersitencia(MarcaDTO marcaDTO) {
        try {
            var marca = new Marca();
            LOGGER.info("Buscando se existe {}", marcaDTO.getNomeMarca());
            buscarNomeMarca(marcaDTO.getNomeMarca()).ifPresentOrElse
                ((value) ->
                    {
                        copiarAtributosIgnorandoNullos(marcaDTO, marca);
                        atualizandoAtributosCliente(
                            Objects.requireNonNull(value), marca);
                    },
                    () -> BeanUtils.copyProperties(marcaDTO, marca));
            return marca;
        } catch (DataIntegrityViolationException | NotFoundException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public void atualizandoAtributosCliente(Marca marcaBD, Marca marca) {
        marca.setId(marcaBD.getId());
    }
}


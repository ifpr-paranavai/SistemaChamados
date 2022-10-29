package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.EquipamentoDTO;
import com.api.sistemachamados.entity.Equipamento;
import com.api.sistemachamados.repository.EquipamentoRepository;
import com.api.sistemachamados.service.EquipamentoService;
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
public class EquipamentoServiceImpl implements EquipamentoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipamentoServiceImpl.class);

    final EquipamentoRepository equipamentoRepository;


    @Override
    public Page<Equipamento> buscarTodos(Pageable pageable) {
        LOGGER.info("Buscando Todas Equipamentos");
        return equipamentoRepository.findAll(pageable);
    }

    @Override
    public Optional<Equipamento> buscarPorId(Long id) throws NotFoundException {
        LOGGER.info("Buscando Equipamento pelo ID: {}", id);
        return Optional.ofNullable(equipamentoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("equipamento.naoEncontrado")));
    }

    @Override
    @Transactional
    public Optional<Equipamento> salvar(EquipamentoDTO equipamentoDTO) {
        try {
            return Optional.of(equipamentoRepository.save(verificaPersitencia(equipamentoDTO)));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public Optional<Equipamento> buscarNumeroSerie(String numeroSerie) throws NotFoundException {
        LOGGER.info("Buscando Equipamento pelo nome: {}", numeroSerie);
        return Optional.ofNullable(equipamentoRepository.findByNumeroSerie(numeroSerie)
            .orElseThrow(() -> new NotFoundException("equipamento.naoEncontrado")));
    }

    @Override
    @Transactional
    public void deletar(Equipamento equipamento) {
        try {
            equipamentoRepository.delete(equipamento);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.deleted.persist $e");
        }
    }

    @Override
    public Equipamento verificaPersitencia(EquipamentoDTO equipamentoDTO) {
        try {
            var equipamento = new Equipamento();

            if (Objects.nonNull(equipamentoDTO.getId())) {
                copiarAtributosIgnorandoNullos(equipamentoDTO, equipamento);
            } else {
                LOGGER.info("Buscando se existe Equipamento");
                equipamentoRepository.findByNumeroSerie(equipamentoDTO.getNumeroSerie()).ifPresentOrElse(
                    value -> {
                        throw new com.api.sistemachamados.exception.DataIntegrityViolationException
                            ("Já existe um Equipamento com esse número de série, informe outro");
                    },
                    () -> BeanUtils.copyProperties(equipamentoDTO, equipamento));
            }
            return equipamento;
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("equipamento.naoEncontrado");
        }
    }

    @Override
    public void atualizandoAtributosEquipamento(Equipamento equipamentoBD, Equipamento equipamento) {
        equipamento.setId(equipamentoBD.getId());
    }
}


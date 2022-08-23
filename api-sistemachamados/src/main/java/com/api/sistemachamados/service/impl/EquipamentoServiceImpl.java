package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.EquipamentoDTO;
import com.api.sistemachamados.entity.Equipamento;
import com.api.sistemachamados.exception.BadRequestException;
import com.api.sistemachamados.repository.EquipamentoRepository;
import com.api.sistemachamados.service.EquipamentoService;
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
public class EquipamentoServiceImpl implements EquipamentoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipamentoServiceImpl.class);

    final EquipamentoRepository equipamentoRepository;


    @Override
    public Page<Equipamento> buscarTodos(Pageable pageable) {
        LOGGER.info("Buscando Todas Equipamentos");
        return equipamentoRepository.findAll(pageable);
    }

    @Override
    public Optional<Equipamento> buscarPorId(Long id) {
        LOGGER.info("Buscando Equipamento pelo ID: {}", id);
        return Optional.ofNullable(equipamentoRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("equipamento.naoEncontrado")));
    }

    @Override
    @Transactional
    public Optional<Equipamento> salvar(EquipamentoDTO equipamentoDTO) {
        try {
            LOGGER.info("Buscando se existe Equipamento");
            var novaEquipamento = new Equipamento();
            var equipamento = equipamentoRepository.findByNumeroSerie(equipamentoDTO.getNumeroSerie());
            if (equipamento.isEmpty()) {
                LOGGER.info("Salvando Equipamento");
                BeanUtils.copyProperties(equipamentoDTO, novaEquipamento);
            } else {
                LOGGER.info("Atualizando Equipamento");
                copiarAtributosIgnorandoNullos(equipamentoDTO, novaEquipamento);
                novaEquipamento.setId(equipamento.get().getId());
            }
            novaEquipamento = equipamentoRepository.save(novaEquipamento);
            return Optional.of(novaEquipamento);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public Optional<Equipamento> buscarNumeroSerie(String numeroSerie) {
        LOGGER.info("Buscando Equipamento pelo nome: {}", numeroSerie);
        return Optional.ofNullable(equipamentoRepository.findByNumeroSerie(numeroSerie)
            .orElseThrow(() -> new BadRequestException("equipamento.naoEncontrado")));
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
}


package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.EquipamentoDTO;
import com.api.sistemachamados.entity.Equipamento;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EquipamentoService {

    Page<Equipamento> buscarTodos(Pageable pageable);

    Optional<Equipamento> buscarPorId(Long id) throws NotFoundException;

    Optional<Equipamento> salvar(EquipamentoDTO equipamentoDTO);

    Optional<Equipamento> buscarNumeroSerie(String numeroSerie) throws NotFoundException;

    void deletar(Equipamento equipamento);

    Equipamento verificaPersitencia(EquipamentoDTO equipamentoDTO);

    void atualizandoAtributosEquipamento(Equipamento equipamentoBD, Equipamento equipamento);
}

package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Equipamento;
import com.api.sistemachamados.entity.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long>, PagingAndSortingRepository<OrdemServico, Long> {

    Optional<OrdemServico> findByData(LocalDate dataEntrada);

    Optional<OrdemServico> findByDataAndEquipamento(LocalDate dataEntrada, Equipamento equipamento);
}

package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Equipamento;
import com.api.sistemachamados.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long>, PagingAndSortingRepository<Equipamento, Long> {

    Optional<Equipamento> findByNumeroSerie(String numeroSerie);
}

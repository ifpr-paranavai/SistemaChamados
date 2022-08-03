package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long>, PagingAndSortingRepository<Equipamento, Long> {
}

package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Saida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SaidaRepository extends JpaRepository<Saida, Long>, PagingAndSortingRepository<Saida, Long> {

    Optional<Saida> findByDataSaidaAndEstaAberto(LocalDate dataSaida, Boolean estaAberto);

    Optional<Saida> findByDataSaida(LocalDate dataEntrada);
}

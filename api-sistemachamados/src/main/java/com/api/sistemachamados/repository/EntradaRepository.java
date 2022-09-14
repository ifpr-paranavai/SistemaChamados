package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface EntradaRepository extends JpaRepository<Entrada, Long>, PagingAndSortingRepository<Entrada, Long> {

    Optional<Entrada> findByDataEntradaAndEstaAberto(LocalDate dataEntrada, Boolean estaAberto);

    Optional<Entrada> findByDataEntrada(LocalDate dataEntrada);
}

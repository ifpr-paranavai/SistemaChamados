package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.EntradaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface EntradaProdutoRepository extends JpaRepository<EntradaProduto, Long>, PagingAndSortingRepository<EntradaProduto, Long> {

    Optional<EntradaProduto> findByDataEntrada(LocalDate dataEntrada);
}

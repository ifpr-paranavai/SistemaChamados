package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Long>, PagingAndSortingRepository<Estoque, Long> {
}

package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Saida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SaidaProdutoRepository extends JpaRepository<Saida, Long>, PagingAndSortingRepository<Saida, Long> {
}

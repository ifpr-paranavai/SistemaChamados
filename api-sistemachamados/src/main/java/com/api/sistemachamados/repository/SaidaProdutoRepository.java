package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.SaidaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SaidaProdutoRepository extends JpaRepository<SaidaProduto, Long>, PagingAndSortingRepository<SaidaProduto, Long> {
}

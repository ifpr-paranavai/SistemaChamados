package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Saida;
import com.api.sistemachamados.entity.SaidaProduto;
import com.api.sistemachamados.entity.SaidaProdutoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SaidaProdutoRepository extends JpaRepository<SaidaProduto, SaidaProdutoId>, PagingAndSortingRepository<SaidaProduto, SaidaProdutoId> {
}

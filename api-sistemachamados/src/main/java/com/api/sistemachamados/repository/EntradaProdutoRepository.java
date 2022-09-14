package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.EntradaProduto;
import com.api.sistemachamados.entity.EntradaProdutoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EntradaProdutoRepository extends JpaRepository<EntradaProduto, EntradaProdutoId>, PagingAndSortingRepository<EntradaProduto, EntradaProdutoId> {
}

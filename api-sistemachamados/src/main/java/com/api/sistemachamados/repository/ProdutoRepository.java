package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Produto;
import com.api.sistemachamados.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, PagingAndSortingRepository<Produto, Long> {

    Optional<Produto> findByNomeProduto(String nomeProduto);
}

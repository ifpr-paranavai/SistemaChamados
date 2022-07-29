package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}

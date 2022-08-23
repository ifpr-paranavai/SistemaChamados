package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.ProdutoDTO;
import com.api.sistemachamados.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProdutoService {

    Page<Produto> buscarTodos(Pageable pageable);

    Optional<Produto> buscarPorId(Long id);

    Optional<Produto> salvar(ProdutoDTO produto);

    Optional<Produto> buscarNomeProduto(String produto);

    void deletar(Produto produto);
}

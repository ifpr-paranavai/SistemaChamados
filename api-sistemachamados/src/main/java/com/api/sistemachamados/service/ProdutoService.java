package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.ProdutoDTO;
import com.api.sistemachamados.entity.EntradaProduto;
import com.api.sistemachamados.entity.Produto;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {

    Page<Produto> buscarTodos(Pageable pageable);

    Optional<Produto> buscarPorId(Long id) throws NotFoundException;

    Optional<EntradaProduto> salvarProduto(List<ProdutoDTO> produtos);

    Optional<Produto> buscarNomeProduto(String produto) throws NotFoundException;

    void deletar(Produto produto);

    List<Produto> verificaPersitencia(List<ProdutoDTO> produtosDTO);

    void atualizandoAtributosProduto(Produto produtoBD, Produto produto, ProdutoDTO produtoDTO);
}

package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.ProdutoDTO;
import com.api.sistemachamados.entity.EntradaProduto;
import com.api.sistemachamados.entity.Produto;
import com.api.sistemachamados.entity.SaidaProduto;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {

    Page<Produto> buscarTodos(Pageable pageable);

    Optional<Produto> buscarPorId(Long id) throws NotFoundException;

    Optional<Produto> salvarProduto(ProdutoDTO produtos);

    Produto calcularEntradaProduto(Produto produto, Integer quantidadeEstoque);

    Produto calcularSaidaProduto(Produto produto, Integer quantidadeEstoque);

    Optional<Produto> buscarNomeProduto(String produto) throws NotFoundException;

    List<EntradaProduto> atribuirProdutoEmListaEntradaProduto(List<EntradaProduto> listaEntradaDTO);

    List<SaidaProduto> atribuirProdutoEmListaSaidaProduto(List<SaidaProduto> saidaProdutos);

    void deletar(Produto produto);

    Produto verificaPersitencia(ProdutoDTO produtoDTO);

    void atualizandoAtributosProduto(Produto produtoBD, Produto produto, ProdutoDTO produtoDTO);
}

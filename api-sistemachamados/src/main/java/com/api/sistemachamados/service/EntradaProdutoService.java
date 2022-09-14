package com.api.sistemachamados.service;

import com.api.sistemachamados.entity.EntradaProduto;
import com.api.sistemachamados.entity.EntradaProdutoId;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EntradaProdutoService {

    Page<EntradaProduto> buscarTodos(Pageable pageable);

    Optional<EntradaProduto> buscarPorId(EntradaProdutoId id) throws NotFoundException;

    Optional<List<EntradaProduto>> salvarEntradaProduto(List<EntradaProduto> entradaProdutos);

    List<EntradaProduto> verificaPersitencia(List<EntradaProduto> entradaProdutos);

    void atualizandoAtributosEntradaProduto(EntradaProduto entradaProdutoBD, EntradaProduto entradaProdutoNovo);
}

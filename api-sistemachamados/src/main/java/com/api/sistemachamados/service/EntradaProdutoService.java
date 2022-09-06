package com.api.sistemachamados.service;

import com.api.sistemachamados.entity.EntradaProduto;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface EntradaProdutoService {

    Page<EntradaProduto> buscarTodos(Pageable pageable);

    Optional<EntradaProduto> buscarPorId(Long id) throws NotFoundException;

    Optional<EntradaProduto> salvarEntradaProduto(EntradaProduto entradaProduto);

    Optional<EntradaProduto> buscarEntradaPorData(LocalDate dataEntrada) throws NotFoundException;

    EntradaProduto verificaPersitencia(EntradaProduto entradaProduto);

    void atualizandoAtributosEntradaProduto(EntradaProduto entradaProdutoBD, EntradaProduto entradaProduto, EntradaProduto entradaProdutoRequest);

}

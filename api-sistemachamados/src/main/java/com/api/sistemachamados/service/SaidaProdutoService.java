package com.api.sistemachamados.service;

import com.api.sistemachamados.entity.SaidaProduto;
import com.api.sistemachamados.entity.SaidaProdutoId;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SaidaProdutoService {

    Page<SaidaProduto> buscarTodos(Pageable pageable);

    Optional<SaidaProduto> buscarPorId(SaidaProdutoId id) throws NotFoundException;

    Optional<List<SaidaProduto>> salvarSaidaProduto(List<SaidaProduto> saidaProdutos);

    List<SaidaProduto> verificaPersitencia(List<SaidaProduto> saidaProdutos);

    void atualizandoAtributosSaidaProduto(SaidaProduto saidaProdutoBD, SaidaProduto saidaProdutoNovo);
}

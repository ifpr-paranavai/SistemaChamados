package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.EntradaDTO;
import com.api.sistemachamados.entity.Entrada;
import com.api.sistemachamados.entity.EntradaProduto;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EntradaService {

    Page<Entrada> buscarTodos(Pageable pageable);

    Optional<Entrada> buscarPorId(Long id) throws NotFoundException;

    Optional<List<EntradaProduto>> salvarEntrada(List<EntradaDTO> entradaProduto);

    Optional<Entrada> buscarEntradaPorData(LocalDate dataEntrada) throws NotFoundException;

    Entrada verificaPersistencia(List<EntradaDTO> entradaProduto);

    BigDecimal calculaValorTotalEntrada(List<EntradaDTO> entradaProdutos);

    void atualizandoAtributosEntrada(Entrada entradaProdutoBD, Entrada entradaProdutoNovo);

    List<EntradaProduto> atribuirEntradaEmListaEntradaProduto(List<EntradaDTO> listaEntradaDTO, Entrada entradaPersistida);

    void deletar(Entrada entrada);

}

package com.api.sistemachamados.service;

import com.api.sistemachamados.entity.Saida;
import com.api.sistemachamados.entity.SaidaProduto;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SaidaService {

    Page<Saida> buscarTodos(Pageable pageable);

    Optional<Saida> buscarPorId(Long id) throws NotFoundException;

    Optional<List<SaidaProduto>> salvarSaida(List<SaidaProduto> saidaProduto);

    Optional<Saida> buscarSaidaPorData(LocalDate dataSaida) throws NotFoundException;

    Saida verificaPersistencia(List<SaidaProduto> saidaProduto);

    BigDecimal calculaValorTotalSaida(List<SaidaProduto> saidaProdutos);

    void atualizandoAtributosSaida(Saida saidaProdutoBD, Saida saidaProdutoNovo);

    List<SaidaProduto> atribuirSaidaEmListaSaidaProduto(List<SaidaProduto> listaSaidaProdutos, Saida saidaPersistida);

    void deletar(Saida saida);

}

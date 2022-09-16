package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.entity.Saida;
import com.api.sistemachamados.entity.SaidaProduto;
import com.api.sistemachamados.repository.SaidaRepository;
import com.api.sistemachamados.service.SaidaProdutoService;
import com.api.sistemachamados.service.SaidaService;
import com.api.sistemachamados.service.ProdutoService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.api.sistemachamados.utils.Utils.copiarAtributosIgnorandoNullos;

@Service
@AllArgsConstructor
public class SaidaServiceImpl implements SaidaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaidaServiceImpl.class);

    final SaidaRepository saidaRepository;

    final SaidaProdutoService saidaProdutoService;

    final ProdutoService produtoService;


    @Override
    public Page<Saida> buscarTodos(Pageable pageable) {
        LOGGER.info("Buscando Todas SaidaProdutos");
        return saidaRepository.findAll(pageable);
    }

    @Override
    public Optional<Saida> buscarPorId(Long id) throws NotFoundException {
        LOGGER.info("Buscando SaidaProduto pelo ID: {}", id);
        return Optional.ofNullable(saidaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("saidaProduto.naoEncontrado")));
    }

    @Override
    @Transactional
    public Optional<List<SaidaProduto>> salvarSaida(List<SaidaProduto> saidaProdutos) {
        try {
            return saidaProdutoService.salvarSaidaProduto(
                atribuirSaidaEmListaSaidaProduto(saidaProdutos, saidaRepository.saveAndFlush(verificaPersistencia(saidaProdutos))));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public Optional<Saida> buscarSaidaPorData(LocalDate dataSaida) throws NotFoundException {
        LOGGER.info("Buscando SaidaProduto pelo nome: {}", dataSaida);
        return Optional.ofNullable(saidaRepository.findByDataSaida(dataSaida)
            .orElseThrow(() -> new NotFoundException("saidaProduto.naoEncontrado")));
    }

    @Override
    public Saida verificaPersistencia(List<SaidaProduto> listaSaidaProdutos) {
        try {
            var saida = new Saida();
            LOGGER.info("Buscando se existe Saida");
            saidaRepository.findByDataSaida(listaSaidaProdutos.get(0).getSaida().getDataSaida())
                .ifPresentOrElse(
                    value -> {
                        if (Boolean.TRUE.equals(value.getEstaAberto())) {
                            LOGGER.info("Atualizando Saida");
                            copiarAtributosIgnorandoNullos(listaSaidaProdutos.get(0).getSaida(), saida);
                            atualizandoAtributosSaida(Objects
                                .requireNonNull(value, "Saida get"), saida);
                        } else {
                            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist.saida");
                        }
                    }, () -> {
                        LOGGER.info("Criando Saida");
                        BeanUtils.copyProperties(listaSaidaProdutos.get(0).getSaida(), saida);
                    });
            saida.setValorTotalSaida(calculaValorTotalSaida(listaSaidaProdutos));
            return saida;
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public List<SaidaProduto> atribuirSaidaEmListaSaidaProduto(List<SaidaProduto> listaSaidaProdutos, Saida saidaPersistida) {
        var listaSaidaProduto = new ArrayList<SaidaProduto>();
        listaSaidaProdutos.forEach(value -> value.setSaida(saidaPersistida));
        BeanUtils.copyProperties(listaSaidaProdutos,listaSaidaProduto);
        return produtoService.atribuirProdutoEmListaSaidaProduto(listaSaidaProduto);
    }

    @Override
    public void atualizandoAtributosSaida(Saida saidaProdutoBD, Saida saidaProdutoNovo) {
        saidaProdutoNovo.setId(saidaProdutoBD.getId());
    }

    @Override
    public BigDecimal calculaValorTotalSaida(List<SaidaProduto> saidaProdutos) {
        return saidaProdutos.stream()
            .filter(p -> Objects.nonNull(p.getValorUnitarioProduto()))
            .map(r -> r.getValorUnitarioProduto().multiply(BigDecimal.valueOf(r.getQuantidadeSaidaProduto())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    @Override
    @Transactional
    public void deletar(Saida saida) {
        try {
            saidaRepository.delete(saida);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.deleted.persist $e");
        }
    }
}


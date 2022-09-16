package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.entity.SaidaProduto;
import com.api.sistemachamados.entity.SaidaProdutoId;
import com.api.sistemachamados.repository.SaidaProdutoRepository;
import com.api.sistemachamados.service.SaidaProdutoService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.api.sistemachamados.utils.Utils.copiarAtributosIgnorandoNullos;

@Service
@AllArgsConstructor
public class SaidaProdutoServiceImpl implements SaidaProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaidaProdutoServiceImpl.class);

    final SaidaProdutoRepository saidaProdutoRepository;

    @Override
    public Page<SaidaProduto> buscarTodos(Pageable pageable) {
        LOGGER.info("Buscando Todas SaidaProdutos");
        return saidaProdutoRepository.findAll(pageable);
    }

    @Override
    public Optional<SaidaProduto> buscarPorId(SaidaProdutoId id) throws NotFoundException {
        LOGGER.info("Buscando SaidaProduto pelo ID: {}", id);
        return Optional.ofNullable(saidaProdutoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("saidaProduto.naoEncontrado")));
    }

    @Override
    @Transactional
    public Optional<List<SaidaProduto>> salvarSaidaProduto(List<SaidaProduto> saidaProdutos) {
        try {
            return Optional.of(saidaProdutoRepository.saveAll(verificaPersitencia((saidaProdutos))));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }


    @Override
    public List<SaidaProduto> verificaPersitencia(List<SaidaProduto> saidaProdutos) {
        try {
            var listaSaidaProduto = new ArrayList<SaidaProduto>();
            LOGGER.info("Buscando se existe SaidaProduto");
            saidaProdutos.forEach(saidaProdutoRequest -> {
                var saidaId = new SaidaProdutoId(saidaProdutoRequest.getSaida().getId(), saidaProdutoRequest.getProduto().getId());
                var saidaProdutoNovo = new SaidaProduto();
                LOGGER.info("Buscando se existe SaidaProduto");
                saidaProdutoRepository.findById(saidaId).ifPresentOrElse(
                    value -> {
                        copiarAtributosIgnorandoNullos(saidaProdutoRequest, saidaProdutoNovo);
                        atualizandoAtributosSaidaProduto(Objects
                            .requireNonNull(value, "SaidaProduto get"), saidaProdutoNovo);
                    },
                    () -> BeanUtils.copyProperties(saidaProdutoRequest, saidaProdutoNovo));
                saidaProdutoNovo.setValorTotalProdutoSaida(calculaValorTotalProdutoSaida(saidaProdutoRequest));
                listaSaidaProduto.add(saidaProdutoNovo);
            });
            return listaSaidaProduto;
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    public BigDecimal calculaValorTotalProdutoSaida(SaidaProduto saidaProduto) {
        return saidaProduto.getValorUnitarioProduto().multiply(BigDecimal.valueOf(saidaProduto.getQuantidadeSaidaProduto()));
    }

    @Override
    public void atualizandoAtributosSaidaProduto(SaidaProduto saidaProdutoBD, SaidaProduto saidaProdutoNovo) {
        saidaProdutoNovo.setId(saidaProdutoBD.getId());
    }
}


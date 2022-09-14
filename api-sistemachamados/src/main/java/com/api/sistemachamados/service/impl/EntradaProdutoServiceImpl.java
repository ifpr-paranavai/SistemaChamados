package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.entity.EntradaProduto;
import com.api.sistemachamados.entity.EntradaProdutoId;
import com.api.sistemachamados.repository.EntradaProdutoRepository;
import com.api.sistemachamados.service.EntradaProdutoService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.api.sistemachamados.utils.Utils.copiarAtributosIgnorandoNullos;

@Service
@AllArgsConstructor
public class EntradaProdutoServiceImpl implements EntradaProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntradaProdutoServiceImpl.class);

    final EntradaProdutoRepository entradaProdutoRepository;

    @Override
    public Page<EntradaProduto> buscarTodos(Pageable pageable) {
        LOGGER.info("Buscando Todas EntradaProdutos");
        return entradaProdutoRepository.findAll(pageable);
    }

    @Override
    public Optional<EntradaProduto> buscarPorId(EntradaProdutoId id) throws NotFoundException {
        LOGGER.info("Buscando EntradaProduto pelo ID: {}", id);
        return Optional.ofNullable(entradaProdutoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("entradaProduto.naoEncontrado")));
    }

    @Override
    @Transactional
    public Optional<List<EntradaProduto>> salvarEntradaProduto(List<EntradaProduto> entradaProdutos) {
        try {
            return Optional.of(entradaProdutoRepository.saveAll(verificaPersitencia((entradaProdutos))));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }


    @Override
    public List<EntradaProduto> verificaPersitencia(List<EntradaProduto> entradaProdutos) {
        try {
            var listaEntradaProduto = new ArrayList<EntradaProduto>();
            LOGGER.info("Buscando se existe EntradaProduto");
            entradaProdutos.forEach(entradaProdutoRequest -> {
                var entradaId = new EntradaProdutoId(entradaProdutoRequest.getEntrada().getId(), entradaProdutoRequest.getProduto().getId());
                var entradaProdutoNovo = new EntradaProduto();
                LOGGER.info("Buscando se existe EntradaProduto");
                entradaProdutoRepository.findById(entradaId).ifPresentOrElse(
                    value -> {
                        copiarAtributosIgnorandoNullos(entradaProdutoRequest, entradaProdutoNovo);
                        atualizandoAtributosEntradaProduto(Objects
                            .requireNonNull(value, "EntradaProduto get"), entradaProdutoNovo);
                    },
                    () -> BeanUtils.copyProperties(entradaProdutoRequest, entradaProdutoNovo));
                entradaProdutoNovo.setValorTotalProdutoEntrada(calculaValorTotalProdutoEntrada(entradaProdutoRequest));
                listaEntradaProduto.add(entradaProdutoNovo);
            });
            return listaEntradaProduto;
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    public BigDecimal calculaValorTotalProdutoEntrada(EntradaProduto entradaProduto) {
        return entradaProduto.getValorUnitarioProduto().multiply(BigDecimal.valueOf(entradaProduto.getQuantidadeProduto()));
    }

    @Override
    public void atualizandoAtributosEntradaProduto(EntradaProduto entradaProdutoBD, EntradaProduto entradaProdutoNovo) {
        entradaProdutoNovo.setId(entradaProdutoBD.getId());
    }
}


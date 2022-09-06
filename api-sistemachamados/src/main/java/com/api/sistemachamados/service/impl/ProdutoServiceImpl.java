package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.ProdutoDTO;
import com.api.sistemachamados.entity.EntradaProduto;
import com.api.sistemachamados.entity.Produto;
import com.api.sistemachamados.repository.ProdutoRepository;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.api.sistemachamados.utils.Utils.copiarAtributosIgnorandoNullos;

@Service
@AllArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoServiceImpl.class);

    final ProdutoRepository produtoRepository;

    final EntradaProdutoService entradaProdutoService;


    @Override
    public Page<Produto> buscarTodos(Pageable pageable) {
        LOGGER.info("Buscando Todas Produtos");
        return produtoRepository.findAll(pageable);
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) throws NotFoundException {
        LOGGER.info("Buscando Produto pelo ID: {}", id);
        return Optional.ofNullable(produtoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("produto.naoEncontrado")));
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<EntradaProduto> salvarProduto(List<ProdutoDTO> produtosDTO) {
        try {
            var listaProdutos = verificaPersitencia(produtosDTO);
            return entradaProdutoService.salvarEntradaProduto(
                new EntradaProduto(produtoRepository
                    .saveAllAndFlush(listaProdutos), calculaValorTotalEntrada(listaProdutos)));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    public BigDecimal calculaValorTotalEntrada(List<Produto> produtos) {
        return produtos.stream()
            .filter(p -> Objects.nonNull(p.getValorCompra()))
            .map(r -> r.getValorCompra().multiply(BigDecimal.valueOf(r.getQuantidadeEstoqueEntrada())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<Produto> verificaPersitencia(List<ProdutoDTO> produtosDTO) {
        try {
            var produtos = new ArrayList<Produto>();
            produtosDTO.forEach(produtoDTO -> {
                var novoProduto = new Produto();
                LOGGER.info("Buscando se existe Produto");
                var produtoBD = produtoRepository.findByNomeProduto(produtoDTO.getNomeProduto());
                produtoBD.ifPresentOrElse(
                    (value) -> {
                        copiarAtributosIgnorandoNullos(produtoDTO, novoProduto);
                        atualizandoAtributosProduto(Objects
                            .requireNonNull(produtoBD.orElse(null), "Produto get"),novoProduto,produtoDTO);
                    },
                    () -> BeanUtils.copyProperties(produtoDTO, novoProduto));
                produtos.add(novoProduto);
            });
            return produtos;
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public void atualizandoAtributosProduto(Produto produtoBD, Produto produto, ProdutoDTO produtoDTO){
        produto.setId(produtoBD.getId());
        produto.setQuantidadeEstoque(produtoBD.getQuantidadeEstoque() + produtoDTO.getQuantidadeEstoqueEntrada());
    }

    @Override
    public Optional<Produto> buscarNomeProduto(String produto) throws NotFoundException {
        LOGGER.info("Buscando Produto pelo nome: {}", produto);
        return Optional.ofNullable(produtoRepository.findByNomeProduto(produto)
            .orElseThrow(() -> new NotFoundException("produto.naoEncontrado")));
    }

    @Override
    @Transactional
    public void deletar(Produto produto) {
        try {
            produtoRepository.delete(produto);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.deleted.persist $e");
        }
    }
}


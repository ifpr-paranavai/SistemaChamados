package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.ProdutoDTO;
import com.api.sistemachamados.entity.EntradaProduto;
import com.api.sistemachamados.entity.Produto;
import com.api.sistemachamados.entity.SaidaProduto;
import com.api.sistemachamados.repository.ProdutoRepository;
import com.api.sistemachamados.service.ProdutoService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.api.sistemachamados.utils.Utils.copiarAtributosIgnorandoNullos;

@Service
@AllArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoServiceImpl.class);

    final ProdutoRepository produtoRepository;


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
    public Optional<Produto> salvarProduto(ProdutoDTO produtosDTO) {
        try {
            return Optional.of(produtoRepository.save(verificaPersitencia(produtosDTO)));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }


    @Override
    public Produto verificaPersitencia(ProdutoDTO produtoDTO) {
        try {
            var produto = new Produto();
            LOGGER.info("Buscando se existe Produto");
            if(Objects.nonNull(produtoDTO.getId())) {
                copiarAtributosIgnorandoNullos(produtoDTO, produto);
            }else {
                produtoRepository.findByNomeProduto(produtoDTO.getNomeProduto()).ifPresentOrElse(
                    value -> {
                        throw new com.api.sistemachamados.exception.DataIntegrityViolationException
                            ("Já existe um produto com esse nome, informe outro");
                    },
                    () -> BeanUtils.copyProperties(produtoDTO, produto));
            }
            return produto;
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public Produto calcularEntradaProduto(Produto produto, Integer quantidadeEstoque) {
        var novoProduto = new Produto();
        produtoRepository.findByNomeProduto(produto.getNomeProduto()).ifPresent(
            value -> novoProduto.setQuantidadeEstoque(value.getQuantidadeEstoque() + quantidadeEstoque));
        return novoProduto;
    }

    @Override
    public Produto calcularSaidaProduto(Produto produto, Integer quantidadeEstoque) {
        var novoProduto = new Produto();
        produtoRepository.findByNomeProduto(produto.getNomeProduto()).ifPresent(
            value -> novoProduto.setQuantidadeEstoque(value.getQuantidadeEstoque() - quantidadeEstoque));
        return novoProduto;
    }

    @Override
    public List<EntradaProduto> atribuirProdutoEmListaEntradaProduto(List<EntradaProduto> entradaProdutos) {
        entradaProdutos.forEach(value ->
            value.setProduto(
                ((ProdutoServiceImpl) AopContext.currentProxy()).salvarProduto(
                    new ProdutoDTO(calcularEntradaProduto(value.getProduto(), value.getQuantidadeProduto()))).orElse(null)));
        return entradaProdutos;
    }

    @Override
    public List<SaidaProduto> atribuirProdutoEmListaSaidaProduto(List<SaidaProduto> saidaProdutos) {
        saidaProdutos.forEach(value ->
            value.setProduto(
                ((ProdutoServiceImpl) AopContext.currentProxy()).salvarProduto(
                    new ProdutoDTO(calcularSaidaProduto(value.getProduto(), value.getQuantidadeSaidaProduto()))).orElse(null)));
        return saidaProdutos;
    }

    @Override
    public void atualizandoAtributosProduto(Produto produtoBD, Produto produto, ProdutoDTO produtoDTO) {
        produto.setId(produtoBD.getId());
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


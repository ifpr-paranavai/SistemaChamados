package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.ProdutoDTO;
import com.api.sistemachamados.entity.Produto;
import com.api.sistemachamados.exception.BadRequestException;
import com.api.sistemachamados.repository.ProdutoRepository;
import com.api.sistemachamados.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Optional<Produto> buscarPorId(Long id) {
        LOGGER.info("Buscando Produto pelo ID: {}", id);
        return Optional.ofNullable(produtoRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("produto.naoEncontrado")));
    }


    @Override
    @Transactional
    public Optional<Produto> salvar(ProdutoDTO produtoDTO) {
        try {
            LOGGER.info("Buscando se existe Produto");
            var novaProduto = new Produto();
            var produto = produtoRepository.findByNomeProduto(produtoDTO.getNomeProduto());
            if (produto.isEmpty()) {
                LOGGER.info("Salvando Produto");
                BeanUtils.copyProperties(produtoDTO, novaProduto);
            } else {
                LOGGER.info("Atualizando Produto");
                copiarAtributosIgnorandoNullos(produtoDTO, novaProduto);
                novaProduto.setId(produto.get().getId());
            }
            novaProduto = produtoRepository.save(novaProduto);
            return Optional.of(novaProduto);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public Optional<Produto> buscarNomeProduto(String produto) {
        LOGGER.info("Buscando Produto pelo nome: {}", produto);
        return Optional.ofNullable(produtoRepository.findByNomeProduto(produto)
            .orElseThrow(() -> new BadRequestException("produto.naoEncontrado")));
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


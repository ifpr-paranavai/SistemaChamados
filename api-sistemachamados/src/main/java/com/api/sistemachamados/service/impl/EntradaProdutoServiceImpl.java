package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.entity.EntradaProduto;
import com.api.sistemachamados.repository.EntradaProdutoRepository;
import com.api.sistemachamados.service.EntradaProdutoService;
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

import java.time.LocalDate;
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
    public Optional<EntradaProduto> buscarPorId(Long id) throws NotFoundException {
        LOGGER.info("Buscando EntradaProduto pelo ID: {}", id);
        return Optional.ofNullable(entradaProdutoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("entradaProduto.naoEncontrado")));
    }

    @Override
    @Transactional
    public Optional<EntradaProduto> salvarEntradaProduto(EntradaProduto entradaProduto) {
        try {
            return Optional.of(entradaProdutoRepository.save(verificaPersitencia(entradaProduto)));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public Optional<EntradaProduto> buscarEntradaPorData(LocalDate dataEntrada) throws NotFoundException {
        LOGGER.info("Buscando EntradaProduto pelo nome: {}", dataEntrada);
        return Optional.ofNullable(entradaProdutoRepository.findByDataEntrada(dataEntrada)
            .orElseThrow(() -> new NotFoundException("entradaProduto.naoEncontrado")));
    }

    @Override
    public EntradaProduto verificaPersitencia(EntradaProduto entradaProdutoRequest) {
        try {
            var entrada = new EntradaProduto();
            LOGGER.info("Buscando se existe EntradaProduto");
            buscarEntradaPorData(entradaProdutoRequest.getDataEntrada()).ifPresentOrElse(
                (value) -> {
                    copiarAtributosIgnorandoNullos(entradaProdutoRequest, entrada);
                    atualizandoAtributosEntradaProduto(Objects
                        .requireNonNull(value, "Entrada Produto Get"), entrada, entradaProdutoRequest);
                },
                () -> BeanUtils.copyProperties(entradaProdutoRequest, entrada));
            return entrada;
        } catch (DataIntegrityViolationException | NotFoundException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }


    @Override
    public void atualizandoAtributosEntradaProduto(EntradaProduto entradaProdutoBD, EntradaProduto entradaProdutoNovo, EntradaProduto entradaProdutoRequest) {
        entradaProdutoNovo.setId(entradaProdutoBD.getId());
        entradaProdutoNovo.setValorTotalEntrada(entradaProdutoBD.getValorTotalEntrada().add(entradaProdutoRequest.getValorTotalEntrada()));
    }
}


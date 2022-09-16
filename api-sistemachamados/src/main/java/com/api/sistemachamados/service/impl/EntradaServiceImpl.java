package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.EntradaDTO;
import com.api.sistemachamados.entity.Entrada;
import com.api.sistemachamados.entity.EntradaProduto;
import com.api.sistemachamados.repository.EntradaRepository;
import com.api.sistemachamados.service.EntradaProdutoService;
import com.api.sistemachamados.service.EntradaService;
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
public class EntradaServiceImpl implements EntradaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntradaServiceImpl.class);

    final EntradaRepository entradaRepository;

    final EntradaProdutoService entradaProdutoService;

    final ProdutoService produtoService;


    @Override
    public Page<Entrada> buscarTodos(Pageable pageable) {
        LOGGER.info("Buscando Todas EntradaProdutos");
        return entradaRepository.findAll(pageable);
    }

    @Override
    public Optional<Entrada> buscarPorId(Long id) throws NotFoundException {
        LOGGER.info("Buscando EntradaProduto pelo ID: {}", id);
        return Optional.ofNullable(entradaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("entradaProduto.naoEncontrado")));
    }

    @Override
    @Transactional
    public Optional<List<EntradaProduto>> salvarEntrada(List<EntradaDTO> entradaDTO) {
        try {
            // TODO: 10/09/2022 Para salvar uma Entrada preciso verificar se ela está aberta na data de hoje!
            //  se estiver aberta, somente adicionar os itens na tabela EntradaProduto com a lista de Produtos
            //  a serem persistidos.
            //  * Calcular o valor de todos os produtos e inserir no valor total - criar função
            //  * Adicionar na Quantidade de produto inserido no estoque do produto em questão
            return entradaProdutoService.salvarEntradaProduto(
                atribuirEntradaEmListaEntradaProduto(entradaDTO, entradaRepository.saveAndFlush(verificaPersistencia(entradaDTO))));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public Optional<Entrada> buscarEntradaPorData(LocalDate dataEntrada) throws NotFoundException {
        LOGGER.info("Buscando EntradaProduto pelo nome: {}", dataEntrada);
        return Optional.ofNullable(entradaRepository.findByDataEntrada(dataEntrada)
            .orElseThrow(() -> new NotFoundException("entradaProduto.naoEncontrado")));
    }

    @Override
    public Entrada verificaPersistencia(List<EntradaDTO> listaEntradaDTO) {
        try {
            var entrada = new Entrada();
            LOGGER.info("Buscando se existe Entrada");
            entradaRepository.findByDataEntrada(listaEntradaDTO.get(0).getEntrada().getDataEntrada())
                .ifPresentOrElse(
                    value -> {
                        if (Boolean.TRUE.equals(value.getEstaAberto())) {
                            LOGGER.info("Atualizando Entrada");
                            copiarAtributosIgnorandoNullos(listaEntradaDTO.get(0).getEntrada(), entrada);
                            atualizandoAtributosEntrada(Objects
                                .requireNonNull(value, "Entrada get"), entrada);
                        } else {
                            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist.entrada");
                        }
                    }, () -> {
                        LOGGER.info("Criando Entrada");
                        BeanUtils.copyProperties(listaEntradaDTO.get(0).getEntrada(), entrada);
                    });
            entrada.setValorTotalEntrada(calculaValorTotalEntrada(listaEntradaDTO));
            return entrada;
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public List<EntradaProduto> atribuirEntradaEmListaEntradaProduto(List<EntradaDTO> listaEntradaDTO, Entrada entradaPersistida) {
        var listaEntradaProduto = new ArrayList<EntradaProduto>();
        listaEntradaDTO.forEach(value -> value.setEntrada(entradaPersistida));
        BeanUtils.copyProperties(listaEntradaDTO,listaEntradaProduto);
        return produtoService.atribuirProdutoEmListaEntradaProduto(listaEntradaProduto);
    }

    @Override
    public void atualizandoAtributosEntrada(Entrada entradaProdutoBD, Entrada entradaProdutoNovo) {
        entradaProdutoNovo.setId(entradaProdutoBD.getId());
    }

    @Override
    public BigDecimal calculaValorTotalEntrada(List<EntradaDTO> entradaProdutos) {
        return entradaProdutos.stream()
            .filter(p -> Objects.nonNull(p.getValorUnitarioProduto()))
            .map(r -> r.getValorUnitarioProduto().multiply(BigDecimal.valueOf(r.getQuantidadeProduto())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    @Override
    @Transactional
    public void deletar(Entrada entrada) {
        try {
            entradaRepository.delete(entrada);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.deleted.persist $e");
        }
    }
}


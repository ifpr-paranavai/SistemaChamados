package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.OrdemServicoDTO;
import com.api.sistemachamados.entity.OrdemServico;
import com.api.sistemachamados.entity.OrdemServicoItem;
import com.api.sistemachamados.repository.OrdemServicoRepository;
import com.api.sistemachamados.service.OrdemServicoItemService;
import com.api.sistemachamados.service.OrdemServicoService;
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

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static com.api.sistemachamados.utils.Utils.copiarAtributosIgnorandoNullos;

@Service
@AllArgsConstructor
public class OrdemServicoServiceImpl implements OrdemServicoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdemServicoServiceImpl.class);

    final OrdemServicoRepository ordemServicoRepository;

    final OrdemServicoItemService ordemServicoItemService;


    @Override
    public Page<OrdemServico> buscarTodos(Pageable pageable) {
        LOGGER.info("Buscando Todas OrdemServicos");
        return ordemServicoRepository.findAll(pageable);
    }

    @Override
    public Optional<OrdemServico> buscarPorId(Long id) throws NotFoundException {
        LOGGER.info("Buscando OrdemServico pelo ID: {}", id);
        return Optional.ofNullable(ordemServicoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("ordemServico.naoEncontrado")));
    }

    @Override
    public Optional<OrdemServico> buscarPorData(LocalDate dataAbertura) throws NotFoundException {
        LOGGER.info("Buscando OrdemServico pela data: {}", dataAbertura);
        return Optional.ofNullable(ordemServicoRepository.findByData(dataAbertura)
            .orElseThrow(() -> new NotFoundException("ordemServico.naoEncontrado")));
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<OrdemServico> salvar(OrdemServicoDTO ordemServicoDTO) {
        try {
            // TODO: 08/09/2022 Caso validação por data e equipamento não for evetiva, validar se OS está aberta
            return Optional.of(Objects.requireNonNull(
                ordemServicoItemService.salvar(
                        defineAtributosOrdemServicoItem(
                            ordemServicoDTO,
                            ordemServicoRepository.saveAndFlush(
                                verificaPersitencia(ordemServicoDTO))))
                    .orElse(null)).getOrdemServico());
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public OrdemServico verificaPersitencia(OrdemServicoDTO ordemServicoDTO) {
        try {
            var ordemServico = new OrdemServico();
            LOGGER.info("Buscando se existe Cliente");
            ordemServicoRepository.findByDataAndEquipamento(ordemServicoDTO.getData(), ordemServicoDTO.getEquipamento())
                .ifPresentOrElse
                    (value ->
                        {
                            copiarAtributosIgnorandoNullos(ordemServicoDTO, ordemServico);
                            atualizandoAtributosOrdemServico(
                                Objects.requireNonNull(value), ordemServico);
                        },
                        () -> BeanUtils.copyProperties(ordemServicoDTO, ordemServico));
            return ordemServico;
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public OrdemServicoItem defineAtributosOrdemServicoItem(OrdemServicoDTO ordemServicoDTO, OrdemServico ordemServico) {
        var osi = ordemServicoDTO.getOrdemServicoItem();
        osi.setOrdemServico(Objects.requireNonNull(ordemServico, "Não foi possível setar objeto OS"));
        return osi;
    }

    @Override
    public void atualizandoAtributosOrdemServico(OrdemServico ordemServicoBD, OrdemServico ordemServico) {
        ordemServico.setId(ordemServicoBD.getId());
    }

    @Override
    @Transactional
    public void deletar(OrdemServico ordemServico) {
        try {
            ordemServicoRepository.delete(ordemServico);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.deleted.persist");
        }
    }
}


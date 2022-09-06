package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.OrdemServicoDTO;
import com.api.sistemachamados.entity.OrdemServico;
import com.api.sistemachamados.entity.OrdemServicoItem;
import com.api.sistemachamados.exception.BadRequestException;
import com.api.sistemachamados.repository.OrdemServicoRepository;
import com.api.sistemachamados.service.OrdemServicoService;
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

import java.util.Optional;

import static com.api.sistemachamados.utils.Utils.copiarAtributosIgnorandoNullos;

@Service
@AllArgsConstructor
public class OrdemServicoServiceImpl implements OrdemServicoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdemServicoServiceImpl.class);

    final OrdemServicoRepository ordemServicoRepository;


    @Override
    public Page<OrdemServico> buscarTodos(Pageable pageable) {
        LOGGER.info("Buscando Todas OrdemServicos");
        return ordemServicoRepository.findAll(pageable);
    }

    @Override
    public Optional<OrdemServico> buscarPorId(Long id) {
        LOGGER.info("Buscando OrdemServico pelo ID: {}", id);
        return Optional.ofNullable(ordemServicoRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("ordemServico.naoEncontrado")));
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<OrdemServico> salvar(OrdemServicoDTO ordemServicoDTO) {
        try {
            LOGGER.info("Buscando se existe OrdemServico");
            var novaOrdemServico = new OrdemServico();
            if (ordemServicoDTO.getSituacaoOs().getSituacao().equals("Aberto")) {
                LOGGER.info("Salvando OrdemServico");
                BeanUtils.copyProperties(ordemServicoDTO, novaOrdemServico);
            } else {
                LOGGER.info("Atualizando OrdemServico");
                copiarAtributosIgnorandoNullos(ordemServicoDTO, novaOrdemServico);
//                novaOrdemServico.setId(ordemServico.get().getId());
            }
            novaOrdemServico = ordemServicoRepository.save(novaOrdemServico);
            return Optional.of(novaOrdemServico);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public Optional<OrdemServicoItem> salvarOrdemServicoItem(OrdemServicoItem ordemServicoItem) {
        return null;
    }

    @Override
    @Transactional
    public void deletar(OrdemServico ordemServico) {
        try {
            ordemServicoRepository.delete(ordemServico);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.deleted.persist $e");
        }
    }
}


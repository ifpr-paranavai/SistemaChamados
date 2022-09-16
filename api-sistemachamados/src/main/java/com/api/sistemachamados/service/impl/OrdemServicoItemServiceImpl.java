package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.entity.OrdemServico;
import com.api.sistemachamados.entity.OrdemServicoItem;
import com.api.sistemachamados.repository.OrdemServicoItemRepository;
import com.api.sistemachamados.service.OrdemServicoItemService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.api.sistemachamados.utils.Utils.copiarAtributosIgnorandoNullos;

@Service
@AllArgsConstructor
public class OrdemServicoItemServiceImpl implements OrdemServicoItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdemServicoItemServiceImpl.class);

    final OrdemServicoItemRepository ordemServicoRepository;

    @Override
    public Optional<OrdemServicoItem> buscarPorOrdemServico(OrdemServico ordemServico) throws NotFoundException {
        LOGGER.info("Buscando OrdemServicoItem pelo ID: {}", ordemServico);
        return Optional.ofNullable(ordemServicoRepository.findByOrdemServico(ordemServico)
            .orElseThrow(() -> new NotFoundException("ordemServico.naoEncontrado")));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<OrdemServicoItem> salvar(OrdemServicoItem ordemServicoItem) {
        try {
            return Optional.of(ordemServicoRepository.save(verificaPersitencia(ordemServicoItem)));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public OrdemServicoItem verificaPersitencia(OrdemServicoItem ordemServicoItem) {
        try {
            var osItem = new OrdemServicoItem();
            LOGGER.info("Buscando se existe OrdemServicoItem");
            ordemServicoRepository.findByOrdemServico(ordemServicoItem.getOrdemServico()).ifPresentOrElse(
                value -> {
                    copiarAtributosIgnorandoNullos(ordemServicoItem, osItem);
                    atualizandoAtributoOrdemServicoItem(Objects
                        .requireNonNull(value, "OrdemServicoItem Get"), osItem);
                },
                () -> BeanUtils.copyProperties(ordemServicoItem, osItem));
            return osItem;
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public void atualizandoAtributoOrdemServicoItem(OrdemServicoItem ordemServicoItemBD, OrdemServicoItem ordemServicoItem) {
        ordemServicoItem.setId(ordemServicoItemBD.getId());
    }


    @Override
    @Transactional
    public void deletar(OrdemServicoItem ordemServicoItem) {
        try {
            ordemServicoRepository.delete(ordemServicoItem);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.deleted.persist $e");
        }
    }
}


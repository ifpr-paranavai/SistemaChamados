package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.ServicoDTO;
import com.api.sistemachamados.entity.Servico;
import com.api.sistemachamados.exception.BadRequestException;
import com.api.sistemachamados.repository.ServicoRepository;
import com.api.sistemachamados.service.ServicoService;
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
public class ServicoServiceImpl implements ServicoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicoServiceImpl.class);

    final ServicoRepository servicoRepository;


    @Override
    public Page<Servico> buscarTodos(Pageable pageable) {
        LOGGER.info("Buscando Todas Servicos");
        return servicoRepository.findAll(pageable);
    }

    @Override
    public Optional<Servico> buscarPorId(Long id) {
        LOGGER.info("Buscando Servico pelo ID: {}", id);
        return Optional.ofNullable(servicoRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("servico.naoEncontrado")));
    }

    @Override
    @Transactional
    public Optional<Servico> salvar(ServicoDTO servicoDTO) {
        try {
            LOGGER.info("Buscando se existe Servico");
            var novaServico = new Servico();
            var servico = servicoRepository.findByNome(servicoDTO.getNome());
            if (servico.isEmpty()) {
                LOGGER.info("Salvando Servico");
                BeanUtils.copyProperties(servicoDTO, novaServico);
            } else {
                LOGGER.info("Atualizando Servico");
                copiarAtributosIgnorandoNullos(servicoDTO, novaServico);
                novaServico.setId(servico.get().getId());
            }
            novaServico = servicoRepository.save(novaServico);
            return Optional.of(novaServico);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public Optional<Servico> buscarNomeServico(String servico) {
        LOGGER.info("Buscando Servico pelo nome: {}", servico);
        return Optional.ofNullable(servicoRepository.findByNome(servico)
            .orElseThrow(() -> new BadRequestException("servico.naoEncontrado")));
    }

    @Override
    @Transactional
    public void deletar(Servico servico) {
        try {
            servicoRepository.delete(servico);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.deleted.persist $e");
        }
    }
}


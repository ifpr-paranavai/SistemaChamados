package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.ServicoDTO;
import com.api.sistemachamados.entity.Cliente;
import com.api.sistemachamados.entity.Servico;
import com.api.sistemachamados.exception.BadRequestException;
import com.api.sistemachamados.repository.ServicoRepository;
import com.api.sistemachamados.service.ServicoService;
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

import java.util.Objects;
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
    public Optional<Servico> buscarPorId(Long id) throws NotFoundException {
        LOGGER.info("Buscando Servico pelo ID: {}", id);
        return Optional.ofNullable(servicoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("servico.naoEncontrado")));
    }

    @Override
    @Transactional
    public Optional<Servico> salvar(ServicoDTO servicoDTO) {
        try {
            return Optional.of(servicoRepository.save(verificaPersitencia(servicoDTO)));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public Servico verificaPersitencia(ServicoDTO servicoDTO) {
        try {
            var servico = new Servico();
            LOGGER.info("Buscando se existe Cliente");
            buscarNomeServico(servicoDTO.getNome()).ifPresentOrElse
                ((value) ->
                    {
                        copiarAtributosIgnorandoNullos(servicoDTO, servico);
                        atualizandoAtributosCliente(
                            Objects.requireNonNull(value), servico);
                    },
                    () -> BeanUtils.copyProperties(servicoDTO, servico));
            return servico;
        } catch (DataIntegrityViolationException | NotFoundException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public void atualizandoAtributosCliente(Servico servicoBD, Servico servico) {
        servico.setId(servicoBD.getId());
    }

    @Override
    public Optional<Servico> buscarNomeServico(String servico) throws NotFoundException {
        LOGGER.info("Buscando Servico pelo nome: {}", servico);
        return Optional.ofNullable(servicoRepository.findByNome(servico)
            .orElseThrow(() -> new NotFoundException("servico.naoEncontrado")));
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


package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.OrdemServicoDTO;
import com.api.sistemachamados.entity.OrdemServico;
import com.api.sistemachamados.entity.OrdemServicoItem;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface OrdemServicoService {

    Page<OrdemServico> buscarTodos(Pageable pageable);

    Optional<OrdemServico> buscarPorId(Long id) throws NotFoundException;

    Optional<OrdemServico> buscarPorData(LocalDate dataAbertura) throws NotFoundException;

    Optional<OrdemServico> salvar(OrdemServicoDTO ordemServicoDTO);

    OrdemServicoItem defineAtributosOrdemServicoItem(OrdemServicoDTO ordemServicoDTO, OrdemServico ordemServico);

    void atualizandoAtributosOrdemServico(OrdemServico ordemServicoBD, OrdemServico ordemServico);

    void deletar(OrdemServico produto);

    OrdemServico verificaPersitencia(OrdemServicoDTO ordemServicoDTO);
}

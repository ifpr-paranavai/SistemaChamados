package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.OrdemServicoDTO;
import com.api.sistemachamados.entity.OrdemServico;
import com.api.sistemachamados.entity.OrdemServicoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrdemServicoService {

    Page<OrdemServico> buscarTodos(Pageable pageable);

    Optional<OrdemServico> buscarPorId(Long id);

    Optional<OrdemServico> salvar(OrdemServicoDTO ordemServicoDTO);

    Optional<OrdemServicoItem> salvarOrdemServicoItem(OrdemServicoItem ordemServicoItem);

    void deletar(OrdemServico produto);
}

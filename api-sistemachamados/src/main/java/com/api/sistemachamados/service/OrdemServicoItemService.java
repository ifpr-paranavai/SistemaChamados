package com.api.sistemachamados.service;

import com.api.sistemachamados.entity.OrdemServico;
import com.api.sistemachamados.entity.OrdemServicoItem;
import javassist.NotFoundException;

import java.util.Optional;

public interface OrdemServicoItemService {

    Optional<OrdemServicoItem> buscarPorOrdemServico(OrdemServico ordemServico) throws NotFoundException;

    Optional<OrdemServicoItem> salvar(OrdemServicoItem ordemServicoItem);

    OrdemServicoItem verificaPersitencia(OrdemServicoItem ordemServicoItem);

    void atualizandoAtributoOrdemServicoItem(OrdemServicoItem ordemServicoItemBD, OrdemServicoItem ordemServicoItem);

    void deletar(OrdemServicoItem ordemServicoItem);
}

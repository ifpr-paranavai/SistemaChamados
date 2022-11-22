package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.OrdemServico;
import com.api.sistemachamados.entity.OrdemServicoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface OrdemServicoItemRepository extends JpaRepository<OrdemServicoItem, Long>, PagingAndSortingRepository<OrdemServicoItem, Long> {

    Optional<OrdemServicoItem> findByOrdemServico(OrdemServico ordemServico);

    Optional<OrdemServicoItem> findByOrdemServicoId(Long ordemServico_id);

}

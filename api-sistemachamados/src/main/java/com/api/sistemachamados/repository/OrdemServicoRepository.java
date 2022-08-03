package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long>, PagingAndSortingRepository<OrdemServico, Long> {
}

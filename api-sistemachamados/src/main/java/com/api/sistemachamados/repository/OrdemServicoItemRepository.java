package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.OrdemServicoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrdemServicoItemRepository extends JpaRepository<OrdemServicoItem, Long>, PagingAndSortingRepository<OrdemServicoItem, Long> {
}

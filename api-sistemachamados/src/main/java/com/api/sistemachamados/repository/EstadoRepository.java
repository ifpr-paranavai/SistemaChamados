package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EstadoRepository extends JpaRepository<Estado, Long>, PagingAndSortingRepository<Estado, Long> {
}

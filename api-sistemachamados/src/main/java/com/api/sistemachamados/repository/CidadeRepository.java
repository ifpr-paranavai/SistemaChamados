package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CidadeRepository extends JpaRepository<Cidade, Long>, PagingAndSortingRepository<Cidade, Long> {
    List<Optional<Cidade>> findByEstadoId(Long estadoId);
}

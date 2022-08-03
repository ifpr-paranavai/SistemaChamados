package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long>, PagingAndSortingRepository<Cidade,Long> {
    Cidade findByNome(String nomeCidade);
}

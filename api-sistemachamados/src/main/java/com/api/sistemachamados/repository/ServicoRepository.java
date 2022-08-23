package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ServicoRepository extends JpaRepository<Servico, Long>, PagingAndSortingRepository<Servico, Long> {

    Optional<Servico> findByNome(String nome);
}

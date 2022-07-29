package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}

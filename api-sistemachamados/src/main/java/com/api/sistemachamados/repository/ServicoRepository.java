package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
}

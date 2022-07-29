package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
}

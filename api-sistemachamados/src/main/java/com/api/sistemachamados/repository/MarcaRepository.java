package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long>, PagingAndSortingRepository<Marca, Long> {
}

package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Marca;
import com.api.sistemachamados.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface MarcaRepository extends JpaRepository<Marca, Long>, PagingAndSortingRepository<Marca, Long> {

    Optional<Marca> findByNomeMarca(String nomeMarca);
}

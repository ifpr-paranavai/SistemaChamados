package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Integer> {

	Optional<Usuario> findByEmail(String email);

    Page<Usuario> findAll(Pageable pageable);

    Optional<Usuario> findById(Long id);
}

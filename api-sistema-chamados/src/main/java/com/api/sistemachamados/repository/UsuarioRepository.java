package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

	Optional<Usuario> findByEmail(String email);

}

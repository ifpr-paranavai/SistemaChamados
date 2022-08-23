package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>, PagingAndSortingRepository<Role, Long> {
    Optional<Role> findByNome(String nome);
}

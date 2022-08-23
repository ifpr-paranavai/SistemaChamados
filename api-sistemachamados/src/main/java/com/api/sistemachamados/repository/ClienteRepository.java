package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long>, PagingAndSortingRepository<Cliente, Long> {

    Optional<Cliente> findByCpfCnpj(String documento);
}

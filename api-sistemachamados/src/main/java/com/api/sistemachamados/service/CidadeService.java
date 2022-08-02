package com.api.sistemachamados.service;

import com.api.sistemachamados.entity.Cidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CidadeService {

    Page<Cidade> findAll(Pageable pageable);

    Optional<Cidade> findById(Long id);

    List<Cidade> saveAll(ArrayList<Cidade> cidade);

    Cidade save(Cidade cidade);

    Cidade findByNomeCidade(String nome);
}

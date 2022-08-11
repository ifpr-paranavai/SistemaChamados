package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.MarcaDTO;
import com.api.sistemachamados.entity.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MarcaService {

    Page<Marca> buscarTodos(Pageable pageable);

    Optional<Marca> buscarPorId(Long id);

    Optional<Marca> salvar(MarcaDTO marca);


    Optional<Marca> buscarNomeMarca(String marca);

    void deletar(Marca marca);
}

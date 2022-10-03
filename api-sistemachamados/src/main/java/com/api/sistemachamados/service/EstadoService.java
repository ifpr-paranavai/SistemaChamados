package com.api.sistemachamados.service;

import com.api.sistemachamados.entity.Estado;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EstadoService {

    Page<Estado> buscarTodos(Pageable pageable);

    Optional<Estado> buscarPorId(Long id) throws NotFoundException;
}

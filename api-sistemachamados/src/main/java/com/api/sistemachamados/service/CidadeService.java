package com.api.sistemachamados.service;

import com.api.sistemachamados.entity.Cidade;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CidadeService {

    Page<Cidade> buscarTodos(Pageable pageable);

    Optional<Cidade> buscarPorId(Long id) throws NotFoundException;

    List<Optional<Cidade>> buscarPorIdEstado(Long id);
}

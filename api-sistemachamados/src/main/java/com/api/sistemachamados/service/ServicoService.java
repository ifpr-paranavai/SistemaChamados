package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.ServicoDTO;
import com.api.sistemachamados.entity.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ServicoService {

    Page<Servico> buscarTodos(Pageable pageable);

    Optional<Servico> buscarPorId(Long id);

    Optional<Servico> salvar(ServicoDTO servico);

    Optional<Servico> buscarNomeServico(String servico);

    void deletar(Servico servico);
}

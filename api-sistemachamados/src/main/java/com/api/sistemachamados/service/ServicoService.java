package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.MarcaDTO;
import com.api.sistemachamados.dto.ServicoDTO;
import com.api.sistemachamados.entity.Marca;
import com.api.sistemachamados.entity.Servico;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ServicoService {

    Page<Servico> buscarTodos(Pageable pageable);

    Optional<Servico> buscarPorId(Long id) throws NotFoundException;

    Optional<Servico> salvar(ServicoDTO servico);

    Optional<Servico> buscarNomeServico(String servico) throws NotFoundException;

    void deletar(Servico servico);

    Servico verificaPersitencia(ServicoDTO servicoDTO);

    void atualizandoAtributosCliente(Servico servicoBD, Servico servico);
}

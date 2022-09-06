package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.ClienteDTO;
import com.api.sistemachamados.dto.MarcaDTO;
import com.api.sistemachamados.entity.Cliente;
import com.api.sistemachamados.entity.Marca;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MarcaService {

    Page<Marca> buscarTodos(Pageable pageable);

    Optional<Marca> buscarPorId(Long id) throws NotFoundException;

    Optional<Marca> salvar(MarcaDTO marca);


    Optional<Marca> buscarNomeMarca(String marca) throws NotFoundException;

    void deletar(Marca marca);

    Marca verificaPersitencia(MarcaDTO marcaDTO);

    void atualizandoAtributosCliente(Marca marcaBD, Marca marca);
}

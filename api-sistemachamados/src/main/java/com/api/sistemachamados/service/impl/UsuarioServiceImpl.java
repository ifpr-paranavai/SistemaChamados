package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.entity.Usuario;
import com.api.sistemachamados.repository.UsuarioRepository;
import com.api.sistemachamados.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    final
    UsuarioRepository usuarioRepository;


    @Override
    public Page<Usuario> findAll(Pageable pageable) {
        LOGGER.info("Buscando Todos Usuários");
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Optional<Usuario> findById(Integer id) {
        LOGGER.info("Buscando Usuário pelo ID: {}", id);
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (isNull(usuario)) {
            throw new RuntimeException("Pessoa não Encontrada");
        }
        return usuario;
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuarioDto) {
        try {
            LOGGER.info("Buscando se existe Usuário");
            var salvarUsuario = new Usuario();
            var usuario = usuarioRepository.findByEmail(usuarioDto.getEmail());
            if (usuario.isEmpty()) {
                LOGGER.info("Salvando Cidade");
                salvarUsuario = usuarioRepository.save(usuarioDto);
            }
            return salvarUsuario;
        } catch (Exception e) {
            LOGGER.error(e.toString(), e);
            throw e;
        }
    }

    @Override
    public Optional<Usuario> findByNome(String email) {
        LOGGER.info("Buscando Usuario pelo nome: {}", email);
        return usuarioRepository.findByEmail(email);
    }
}

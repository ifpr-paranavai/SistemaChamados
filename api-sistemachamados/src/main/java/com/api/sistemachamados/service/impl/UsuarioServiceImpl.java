package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.UsuarioDTO;
import com.api.sistemachamados.entity.Usuario;
import com.api.sistemachamados.exception.BadRequestException;
import com.api.sistemachamados.repository.UsuarioRepository;
import com.api.sistemachamados.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    final UsuarioRepository usuarioRepository;


    @Override
    public Page<Usuario> buscarTodos(Pageable pageable) {
        LOGGER.info("Buscando Todos Usuários");
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        LOGGER.info("Buscando Usuário pelo ID: {}", id);
        return Optional.ofNullable(usuarioRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("Usuário não Encontrado")));
    }

    @Override
    @Transactional
    public Optional<Usuario> salvar(UsuarioDTO usuarioDto) {
        try {
            LOGGER.info("Buscando se existe Usuário");
            var usuarioNovo = new Usuario();
            var usuario = buscarPorEmail(usuarioDto.getEmail());
            if (usuario.isEmpty()) {
                LOGGER.info("Salvando Usuário");
                BeanUtils.copyProperties(usuarioDto, usuarioNovo);
                usuarioNovo.setSenha(new BCryptPasswordEncoder().encode(usuarioDto.getSenha()));
                usuarioNovo.setDeleted(false);
            } else {
                LOGGER.info("Atualizando Usuário");
                BeanUtils.copyProperties(usuarioDto, usuarioNovo);
                usuarioNovo.setId(usuario.get().getId());
            }
            usuarioNovo = usuarioRepository.save(usuarioNovo);
            return Optional.of(usuarioNovo);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("Ocorreu um erro ao salvar Objeto!!! " + e);
        }
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        LOGGER.info("Buscando Usuário pelo e-mail: {}", email);
        return Optional.ofNullable(usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new BadRequestException("Usuário não Encontrado")));
    }

    @Override
    @Transactional
    public void deletar(Usuario usuario) {
        try {
            usuarioRepository.delete(usuario);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("Ocorreu um erro ao apagar Objeto!!! " + e);
        }
    }
}


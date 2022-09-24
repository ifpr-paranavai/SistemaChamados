package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.UsuarioDTO;
import com.api.sistemachamados.entity.Usuario;
import com.api.sistemachamados.repository.UsuarioRepository;
import com.api.sistemachamados.service.UsuarioService;
import javassist.NotFoundException;
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

import java.util.Objects;
import java.util.Optional;

import static com.api.sistemachamados.utils.Utils.copiarAtributosIgnorandoNullos;

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
    public Optional<Usuario> buscarPorId(Long id) throws NotFoundException {
        LOGGER.info("Buscando Usuário pelo ID: {}", id);
        return Optional.ofNullable(usuarioRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Usuário não Encontrado")));
    }

    @Override
    @Transactional
    public Optional<Usuario> salvar(UsuarioDTO usuarioDto) {
        try {
            return Optional.of(usuarioRepository.save(verificaPersitencia(usuarioDto)));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("Ocorreu um erro ao salvar Objeto!!! " + e);
        }
    }

    @Override
    public Usuario verificaPersitencia(UsuarioDTO usuarioDTO) {
        try {
            var usuario = new Usuario();
            LOGGER.info("Buscando se existe Usuário");
            usuarioRepository.findByEmail(usuarioDTO.getEmail()).ifPresentOrElse
                (value ->
                    {
                        copiarAtributosIgnorandoNullos(usuarioDTO, usuario);
                        atualizandoAtributosCliente(
                            Objects.requireNonNull(value), usuario, usuarioDTO);
                    },
                    () ->
                    {
                        BeanUtils.copyProperties(usuarioDTO, usuario);
                        usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioDTO.getSenha()));
                    });
            return usuario;
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public void atualizandoAtributosCliente(Usuario usuarioBD, Usuario usuario, UsuarioDTO usuarioDTO) {
        usuario.setId(usuarioBD.getId());
        if (Objects.nonNull(usuarioDTO.getSenha())) {
            usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioDTO.getSenha()));
        }
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) throws NotFoundException {
        LOGGER.info("Buscando Usuário pelo e-mail: {}", email);
        return Optional.ofNullable(usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("Usuário não Encontrado")));
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


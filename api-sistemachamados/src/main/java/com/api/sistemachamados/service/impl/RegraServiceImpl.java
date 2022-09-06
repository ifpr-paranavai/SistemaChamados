package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.RegraDTO;
import com.api.sistemachamados.entity.Role;
import com.api.sistemachamados.repository.RoleRepository;
import com.api.sistemachamados.service.RegraService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

import static com.api.sistemachamados.utils.Utils.copiarAtributosIgnorandoNullos;

@Service
@AllArgsConstructor
public class RegraServiceImpl implements RegraService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegraServiceImpl.class);

    final
    RoleRepository roleRepository;


    @Override
    public Page<Role> buscarTodos(Pageable pageable) {
        LOGGER.info("Buscando Todas Regras");
        return roleRepository.findAll(pageable);
    }

    @Override
    public Optional<Role> buscarPorId(Long id) throws NotFoundException {
        LOGGER.info("Buscando Regra pelo ID: {}", id);
        return Optional.ofNullable(roleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("role.naoEncontrado")));
    }

    @Override
    @Transactional
    public Optional<Role> salvar(RegraDTO roleDto) {
        try {
            return Optional.of(roleRepository.save(verificaPersitencia(roleDto)));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public Role verificaPersitencia(RegraDTO roleDTO) {
        try {
            var role = new Role();
            LOGGER.info("Buscando se existe Regra");
            buscarPorNome(roleDTO.getNome()).ifPresentOrElse
                ((value) ->
                    {
                        copiarAtributosIgnorandoNullos(roleDTO, role);
                        atualizandoAtributosCliente(
                            Objects.requireNonNull(value), role);
                    },
                    () -> BeanUtils.copyProperties(roleDTO, role));
            return role;
        } catch (DataIntegrityViolationException | NotFoundException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public void atualizandoAtributosCliente(Role roleBD, Role role) {
        role.setId(roleBD.getId());
    }

    @Override
    public Optional<Role> buscarPorNome(String nome) throws NotFoundException {
        LOGGER.info("Buscando Regra pelo e-mail: {}", nome);
        return Optional.ofNullable(roleRepository.findByNome(nome)
            .orElseThrow(() -> new NotFoundException("role.naoEncontrado")));
    }

    @Override
    public void deletar(Role role) {
        try {
            roleRepository.delete(role);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.deleted.persist $e");
        }
    }
}

package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.dto.RegraDTO;
import com.api.sistemachamados.entity.Role;
import com.api.sistemachamados.exception.BadRequestException;
import com.api.sistemachamados.repository.RoleRepository;
import com.api.sistemachamados.service.RegraService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public Optional<Role> buscarPorId(Long id) {
        LOGGER.info("Buscando Regra pelo ID: {}", id);
        return Optional.ofNullable(roleRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("role.naoEncontrado")));
    }

    @Override
    @Transactional
    public Optional<Role> salvar(RegraDTO roleDto) {
        try {
            LOGGER.info("Buscando se existe Regra");
            var salvarRole = new Role();
            var role = roleRepository.findByNome(roleDto.getNome());
            if (role.isEmpty()) {
                LOGGER.info("Salvando Regra");
                BeanUtils.copyProperties(roleDto, role);
            } else {
                LOGGER.info("Atualizando Regra");
                copiarAtributosIgnorandoNullos(roleDto, role);
                salvarRole.setId(role.get().getId());
            }
            salvarRole = roleRepository.save(salvarRole);
            return Optional.of(salvarRole);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.toString(), e);
            throw new com.api.sistemachamados.exception.DataIntegrityViolationException("error.save.persist");
        }
    }

    @Override
    public Optional<Role> buscarPorNome(String nome) {
        LOGGER.info("Buscando Regra pelo e-mail: {}", nome);
        return Optional.ofNullable(roleRepository.findByNome(nome)
            .orElseThrow(() -> new BadRequestException("role.naoEncontrado")));
    }

    @Override
    public void deletar(Role role) {
        LOGGER.info("Buscando Regra pelo ID: {}", role.getId());
        var roleSalvo = buscarPorId(role.getId());
        BeanUtils.copyProperties(roleSalvo, role);
        roleRepository.delete(role);
    }
}

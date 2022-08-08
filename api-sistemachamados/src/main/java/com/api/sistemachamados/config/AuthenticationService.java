package com.api.sistemachamados.config;

import com.api.sistemachamados.entity.Usuario;
import com.api.sistemachamados.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService implements UserDetailsService {

    final UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> optional = repository.findByEmail(username);

        if (optional.isPresent()) {
            return optional.get();
        }

        //        throw another excpetion
        throw new UsernameNotFoundException("usuario.naoEncontrado");
    }

}

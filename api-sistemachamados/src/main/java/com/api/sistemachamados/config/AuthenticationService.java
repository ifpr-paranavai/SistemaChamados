package com.api.sistemachamados.config;

import com.api.sistemachamados.entity.Usuario;
import com.api.sistemachamados.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> optional = repository.findByEmail(username);

        if(optional.isPresent()) {
            return optional.get();
        }

        //        throw another excpetion
        throw new UsernameNotFoundException("User not found");
    }

}

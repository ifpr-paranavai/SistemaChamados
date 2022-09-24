package com.api.sistemachamados.service;

import com.api.sistemachamados.dto.ApiResponseDTO;
import com.api.sistemachamados.dto.LoginDTO;
import com.api.sistemachamados.dto.TokenDTO;
import com.api.sistemachamados.entity.Usuario;
import com.api.sistemachamados.security.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static com.api.sistemachamados.utils.Utils.setHandlerResponse;

@Service
@AllArgsConstructor
public class AuthService {

    final AuthenticationManager authenticationManager;

    final TokenService tokenService;

    public ApiResponseDTO<Object> login(LoginDTO loginDTO) {
        try {
            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
            var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            Usuario usuario = (Usuario) authentication.getPrincipal();
            String token = tokenService.generateJwtToken(usuario);
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setType("Bearer");
            tokenDTO.setToken(token);
            return setHandlerResponse(tokenDTO, HttpStatus.OK, "Login realizado com sucesso");
        } catch (Exception e) {
            return setHandlerResponse(null, HttpStatus.BAD_REQUEST, "Credenciais incorretas");
        }
    }
}

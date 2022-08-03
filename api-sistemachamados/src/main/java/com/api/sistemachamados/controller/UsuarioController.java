package com.api.sistemachamados.controller;

import com.api.sistemachamados.dto.UsuarioDTO;
import com.api.sistemachamados.entity.Usuario;
import com.api.sistemachamados.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/usuario")
@AllArgsConstructor
@SecurityRequirement(name = "sistemachamadosapi")
public class UsuarioController {

    final UsuarioService usuarioService;

    @PostMapping("/criar-usuario")
    public ResponseEntity<Object> criarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        var usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);
        return new ResponseEntity<>(usuarioService.salvar(usuario), HttpStatus.CREATED);
    }
}

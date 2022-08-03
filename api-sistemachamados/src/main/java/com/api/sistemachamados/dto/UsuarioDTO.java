package com.api.sistemachamados.dto;

import com.api.sistemachamados.entity.Role;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class UsuarioDTO {

    @NotEmpty(message = "Nome não pode ser nulo ou vazio")
    private String nome;

    @NotEmpty(message = "Senha não pode ser nulo ou vazio")
    private String senha;

    private String email;

    private String telefone;

    @NotNull
    private Set<Role> roles;
}

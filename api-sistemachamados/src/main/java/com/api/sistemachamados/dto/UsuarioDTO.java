package com.api.sistemachamados.dto;

import com.api.sistemachamados.entity.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class UsuarioDTO {

    @NotEmpty(message = "Nome não pode ser nulo ou vazio")
    private String nome;

    @NotEmpty(message = "Senha não pode ser nulo ou vazio")
    private String senha;

    @NotEmpty(message = "email.vazioNulo")
    @Email
    private String email;

    private String telefone;

    @NotNull
    private Collection<Role> roles = new ArrayList<>();
}

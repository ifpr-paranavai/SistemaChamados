package com.api.sistemachamados.dto;

import com.api.sistemachamados.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class UsuarioDTO {

    @Schema(
        example = "João da Silva",
        description = "Identificação do nome do usuário",
        required = true
    )
    @JsonProperty("nome")
    @NotEmpty(message = "Nome não pode ser nulo ou vazio")
    private String nome;

    @Schema(
        example = "$%LBo4Ndem1Sho#$",
        description = "Senha para salvar no usuário",
        required = true
    )
    @JsonProperty("senha")
    @NotEmpty(message = "Senha não pode ser nulo ou vazio")
    private String senha;

    @Schema(
        example = "joao@gmail.com.br",
        description = "Senha para salvar no usuário",
        required = true
    )
    @JsonProperty("email")
    @NotEmpty(message = "email.vazioNulo")
    @Email
    private String email;

    @Schema(
        example = "(41) 99154-4057",
        description = "Telefone do usuário",
        required = true
    )
    @JsonProperty("telefone")
    private String telefone;

    @Schema(
        example = "[1,2,3]",
        description = "Objeto de Regras",
        required = true
    )
    @JsonProperty("roles")
    @NotNull
    private Collection<Role> roles = new ArrayList<>();
}

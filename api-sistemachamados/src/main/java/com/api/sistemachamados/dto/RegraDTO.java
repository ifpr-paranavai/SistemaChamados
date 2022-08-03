package com.api.sistemachamados.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegraDTO {

    @NotEmpty(message = "Nome não pode ser nulo ou vazio")
    private String nome;
}

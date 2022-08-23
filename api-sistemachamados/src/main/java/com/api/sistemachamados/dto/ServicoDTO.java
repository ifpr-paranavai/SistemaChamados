package com.api.sistemachamados.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ServicoDTO {

    @Schema(
        example = "Limpeza Equipamento",
        description = "Identificação do nome de um serviço realizado",
        required = true
    )
    @JsonProperty("nome")
    @NotEmpty(message = "nome.null")
    private String nome;


    @Schema(
        example = "Este serviço realiza limpeza do equipamento",
        description = "observacao",
        required = true
    )
    @JsonProperty("observacao")
    private String observacao;
}

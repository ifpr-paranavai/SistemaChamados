package com.api.sistemachamados.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MarcaDTO {

    @Schema(
        example = "1",
        description = "Identificação da Marca de um equipamento"
    )
    @JsonProperty("id")
    private Long id;

    @Schema(
        example = "Elma Chips",
        description = "Identificação da Marca de um equipamento",
        required = true
    )
    @JsonProperty("nomeMarca")
    @NotEmpty(message = "Nome não pode ser nulo ou vazio")
    private String nomeMarca;
}

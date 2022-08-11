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
public class MarcaDTO {

    @Schema(
        example = "Elma Chips",
        description = "Identificação da Marca de um equipamento",
        required = true
    )
    @JsonProperty("nomeMarca")
    @NotEmpty(message = "Nome não pode ser nulo ou vazio")
    private String nomeMarca;
}

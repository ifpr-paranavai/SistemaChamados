package com.api.sistemachamados.dto;

import com.api.sistemachamados.entity.Cliente;
import com.api.sistemachamados.entity.Marca;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class EquipamentoDTO {

    @Schema(
        example = "1L",
        description = "Identificação do equipamento"
    )
    @JsonProperty("id")
    private Long id;

    @Schema(
        example = "Ar Condicionado",
        description = "Identificação equipamento",
        required = true
    )
    @JsonProperty("nomeEquipamento")
    @NotEmpty(message = "nome.null")
    private String nomeEquipamento;

    @Schema(
        example = "220V",
        description = "Tensão elétrica do equipamento",
        required = true
    )
    @JsonProperty("tensao")
    @NotEmpty(message = "tensao.null")
    private String tensao;

    @Schema(
        example = "14A",
        description = "Amperagem elétrica do equipamento"
    )
    @JsonProperty("amperagem")
    private String amperagem;

    @Schema(
        example = "1445214ABC5111",
        description = "Número de Série do Equipamento",
        required = true
    )
    @JsonProperty("numeroSerie")
    private String numeroSerie;

    @Schema(
        example = "1445214BRASIL",
        description = "Tag do equipamento"
    )
    @JsonProperty("tag")
    private String tag;

    @Schema(
        example = "Equipamento AR 12000BTUS",
        description = "Especificação técnica do Equipamento"
    )
    @JsonProperty("especificacaoTecnica")
    private String especificacaoTecnica;

    @Schema(
        example = "Objeto",
        description = "Objeto Marca"
    )
    @JsonProperty("marca")
    private Marca marca;

    @Schema(
        example = "Objeto",
        description = "Objeto Cliente"
    )
    @JsonProperty("cliente")
    private Cliente cliente;
}

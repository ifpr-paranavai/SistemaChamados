package com.api.sistemachamados.dto;

import com.api.sistemachamados.entity.*;
import com.api.sistemachamados.enums.SituacaoOsEnum;
import com.api.sistemachamados.enums.TipoAtendimentoEnum;
import com.api.sistemachamados.enums.TipoOrdemServicoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class OrdemServicoDTO {

    @Schema(
        example = "21/01/2022",
        description = "Data da Ordem de Serviço",
        required = true
    )
    @JsonProperty("data")
    @NotEmpty(message = "data.null")
    private LocalDate data;

    @Schema(
        example = "Objeto",
        description = "Objeto cliente"
    )
    @JsonProperty("cliente")
    @NotEmpty(message = "obj.null")
    private Cliente cliente;

    @Schema(
        example = "Objeto",
        description = "Objeto cliente"
    )
    @JsonProperty("usuario")
    @NotEmpty(message = "obj.null")
    private Usuario usuario;

    @Schema(
        example = "SPLIT",
        description = "Tipo da Ordem de Serviço",
        required = true
    )
    @JsonProperty("tipoOrdemServico")
    @NotEmpty(message = "tipoOrdemServico.null")
    @Enumerated(EnumType.STRING)
    private TipoOrdemServicoEnum tipoOrdemServico;

    @Schema(
        example = "Aberto",
        description = "Tipo da Situação da OS",
        required = true
    )
    @JsonProperty("situacaoOs")
    @NotEmpty(message = "situacao.null")
    @Enumerated(EnumType.STRING)
    private SituacaoOsEnum situacaoOs;

    @Schema(
        example = "Objeto",
        description = "Objeto produto"
    )
    @JsonProperty("produto")
    @NotEmpty(message = "obj.null")
    private Produto produto;

    @Schema(
        example = "Preventiva",
        description = "Tipo do atendimento",
        required = true
    )
    @JsonProperty("tipoAtendimento")
    @NotEmpty(message = "tipoAtendimento.null")
    @Enumerated(EnumType.STRING)
    private TipoAtendimentoEnum tipoAtendimento;

    @Schema(
        example = "[1,2,3]",
        description = "Objeto de Regras",
        required = true
    )
    @JsonProperty("servicos")
    @NotEmpty(message = "servicos.null")
    private Collection<Servico> servicos = new ArrayList<>();

    @Schema(
        example = "Objeto",
        description = "Objeto equipamento",
        required = true
    )
    @JsonProperty("equipamento")
    @NotEmpty(message = "obj.null")
    private Equipamento equipamento;

    @Schema(
        example = "Objeto",
        description = "Objeto ordemServicoItem"
    )
    @JsonProperty("ordemServicoItem")
    @NotEmpty(message = "obj.null")
    private OrdemServicoItem ordemServicoItem;


    @Schema(
        example = "[1,2,3]",
        description = "Objeto de Produtos"
    )
    @JsonProperty("produtos")
    private ArrayList<Produto> produtos = new ArrayList<>();
}

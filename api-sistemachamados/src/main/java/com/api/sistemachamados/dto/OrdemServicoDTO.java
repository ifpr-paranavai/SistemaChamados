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
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "data.null")
    private LocalDate data;

    @Schema(
        example = "Objeto",
        description = "Objeto cliente"
    )
    @JsonProperty("cliente")
    @NotNull(message = "obj.null")
    private Cliente cliente;

    @Schema(
        example = "Objeto",
        description = "Objeto usuario"
    )
    @JsonProperty("usuario")
    @NotNull(message = "obj.null")
    private Usuario usuario;

    @Schema(
        example = "SPLIT",
        description = "Tipo da Ordem de Serviço",
        required = true
    )
    @JsonProperty("tipoOrdemServico")
    @Enumerated(EnumType.STRING)
    private TipoOrdemServicoEnum tipoOrdemServico;

    @Schema(
        example = "Aberto",
        description = "Tipo da Situação da OS"
    )
    @JsonProperty("situacaoOs")
    @Enumerated(EnumType.STRING)
    private SituacaoOsEnum situacaoOs;

    @Schema(
        example = "Objeto",
        description = "Objeto produto"
    )
    @JsonProperty("produto")
    private Produto produto;

    @Schema(
        example = "Preventiva",
        description = "Tipo do atendimento",
        required = true
    )
    @JsonProperty("tipoAtendimento")
    @Enumerated(EnumType.STRING)
    private TipoAtendimentoEnum tipoAtendimento;

    @Schema(
        example = "[1,2,3]",
        description = "Objeto de Regras",
        required = true
    )
    @JsonProperty("servicos")
    @NotNull(message = "servicos.null")
    private Collection<Servico> servicos = new ArrayList<>();

    @Schema(
        example = "Objeto",
        description = "Objeto equipamento",
        required = true
    )
    @JsonProperty("equipamento")
    @NotNull(message = "obj.null")
    private Equipamento equipamento;

    @Schema(
        example = "Objeto",
        description = "Objeto ordemServicoItem"
    )
    @JsonProperty("ordemServicoItem")
    @NotNull(message = "obj.null")
    private OrdemServicoItem ordemServicoItem;


    @Schema(
        example = "[1,2,3]",
        description = "Objeto de Produtos"
    )
    @JsonProperty("produtos")
    private ArrayList<Produto> produtos = new ArrayList<>();

    @Schema(
        example = "1",
        description = "Quantidade de Produtos"
    )
    @JsonProperty("qtdProdutos")
    private Integer qtdProdutos;
}

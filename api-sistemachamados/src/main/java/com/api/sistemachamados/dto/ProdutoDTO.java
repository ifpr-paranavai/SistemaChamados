package com.api.sistemachamados.dto;

import com.api.sistemachamados.entity.Marca;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class ProdutoDTO {

    @Schema(
        example = "Parafuso",
        description = "Identificação do nome do produto",
        required = true
    )
    @JsonProperty("nomeProduto")
    @NotEmpty(message = "nome.null")
    private String nomeProduto;

    @Schema(
        example = "10un",
        description = "10 unidades",
        required = true
    )
    @JsonProperty("quantidadeEstoque")
    private Integer quantidadeEstoque;

    @Schema(
        example = "10un",
        description = "10 unidades",
        required = true
    )
    @JsonProperty("quantidadeEstoqueEntrada")
    private Integer quantidadeEstoqueEntrada;

    @Schema(
        example = "25.50",
        description = "Valor da compra do produto",
        required = true
    )
    @JsonProperty("valorCompra")
    private BigDecimal valorCompra;

    @Schema(
        example = "25.50",
        description = "Valor da compra do produto",
        required = true
    )
    @JsonProperty("valorCompraEntrada")
    private BigDecimal valorCompraEntrada;

    @Schema(
        example = "33.50",
        description = "Vaor do produto a ser vendido",
        required = true
    )
    @JsonProperty("valorVenda")
    private BigDecimal valorVenda;

    @Schema(
        example = "33.50",
        description = "Vaor do produto a ser vendido",
        required = true
    )
    @JsonProperty("valorVendaEntrada")
    private BigDecimal valorVendaEntrada;

    @Schema(
        example = "Objeto",
        description = "Objeto Marca"
    )
    @JsonProperty("marca")
    private Marca marca;
}

package com.api.sistemachamados.dto;

import com.api.sistemachamados.entity.Marca;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
    @NotEmpty(message = "qtd.null")
    private Integer quantidadeEstoque;

    @Schema(
        example = "25.50",
        description = "Valor da compra do produto",
        required = true
    )
    @JsonProperty("valorCompra")
    @NotEmpty(message = "valor.null")
    private BigDecimal valorCompra;

    @Schema(
        example = "33.50",
        description = "Vaor do produto a ser vendido",
        required = true
    )
    @JsonProperty("valorVenda")
    @NotEmpty(message = "valor.null")
    private BigDecimal valorVenda;

    @Schema(
        example = "Objeto",
        description = "Objeto Marca"
    )
    @JsonProperty("marca")
    @NotEmpty(message = "obj.null")
    private Marca marca;
}

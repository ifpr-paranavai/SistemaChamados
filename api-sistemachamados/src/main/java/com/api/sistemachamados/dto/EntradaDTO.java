package com.api.sistemachamados.dto;

import com.api.sistemachamados.entity.Entrada;
import com.api.sistemachamados.entity.Produto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
public class EntradaDTO {

    // TODO: 08/09/2022 criar itens produto com lista de EntradaProduto
    @Schema(
        example = "001521AB",
        description = "Identificação da Nota Fiscal"
    )
    @JsonProperty("valorTotalProdutoEntrada")
    @Min(value = 1, message = "valor.min")
    private BigDecimal valorTotalProdutoEntrada;

    @Schema(
        example = "21/01/2022",
        description = "Data da Entrada",
        required = true
    )
    @JsonProperty("valorUnitarioProduto")
    @Min(value = 1, message = "valor.min")
    private BigDecimal valorUnitarioProduto;

    @Schema(
        example = "True",
        description = "Informação se está aberto a Entrada",
        required = true
    )
    @JsonProperty("quantidadeProduto")
    @Min(value = 1, message = "qtd.min")
    private Integer quantidadeProduto;

    @Schema(
        example = "Objeto",
        description = "Objeto Produto",
        required = true
    )
    @JsonProperty("produto")
    private Produto produto;

    @Schema(
        example = "Objeto",
        description = "Objeto Entrada",
        required = true
    )
    @JsonProperty("entrada")
    private Entrada entrada;
}

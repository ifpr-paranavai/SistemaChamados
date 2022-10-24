package com.api.sistemachamados.dto;

import com.api.sistemachamados.entity.Marca;
import com.api.sistemachamados.entity.Produto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoDTO {

    @Schema(
        example = "Parafuso",
        description = "Identificação do nome do produto"
    )
    @JsonProperty("id")
    private Long id;

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
        description = "10 unidades"
    )
    @JsonProperty("quantidadeEstoque")
    private Integer quantidadeEstoque;

    @Schema(
        example = "25.50",
        description = "Valor da compra do produto",
        required = true
    )
    @JsonProperty("valorCompra")
    private BigDecimal valorCompra;

    @Schema(
        example = "33.50",
        description = "Valor do produto a ser vendido",
        required = true
    )
    @JsonProperty("valorVenda")
    private BigDecimal valorVenda;

    @Schema(
        example = "Objeto",
        description = "Objeto Marca"
    )
    @JsonProperty("marca")
    private Marca marca;

    public ProdutoDTO(Produto produto) {
        this.nomeProduto = produto.getNomeProduto();
        this.quantidadeEstoque = produto.getQuantidadeEstoque();
        this.valorCompra = produto.getValorCompra();
        this.valorVenda = produto.getValorVenda();
        this.marca = produto.getMarca();
    }

    public Produto toProduto() {
        return new Produto(this.id, this.nomeProduto, this.quantidadeEstoque, this.valorCompra, this.valorVenda, this.marca);
    }
}

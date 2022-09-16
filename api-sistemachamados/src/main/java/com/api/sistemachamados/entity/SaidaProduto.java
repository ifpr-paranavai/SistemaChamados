package com.api.sistemachamados.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Table(name = "saida_produto")
public class SaidaProduto extends Auditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SaidaProdutoId id;

    // TODO: 08/09/2022 valor a ser calculado qtd x valor unitário
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valorTotalProdutoSaida;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valorUnitarioProduto;

    @Column(nullable = false, precision = 19, scale = 2)
    private Integer quantidadeSaidaProduto;

    @ManyToOne
    @JoinColumn(name = "produto_id", insertable = false, updatable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "saida_id", insertable = false, updatable = false)
    private Saida saida;

    @ManyToOne
    @JoinColumn()
    private OrdemServico ordemServico;

    public SaidaProduto(Produto produto, Saida saida, OrdemServico ordemServico){
        this.produto = produto;
        this.saida = saida;
        this.ordemServico = ordemServico;
    }
}


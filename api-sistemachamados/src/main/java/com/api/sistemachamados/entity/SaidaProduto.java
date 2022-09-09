package com.api.sistemachamados.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.AUTO;

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

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    // TODO: 08/09/2022 valor a ser calculado qtd x valor unitário
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valorTotalProdutoSaida;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valorUnitarioProduto;

    @Column(nullable = false, precision = 19, scale = 2)
    private Integer quantidadeProduto;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Saida saida;
}


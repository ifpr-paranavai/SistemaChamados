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
@Table(name = "produto")
public class Produto extends Auditoria implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(nullable = false,unique = true)
    private String nomeProduto;

    @Column(nullable = false)
    private Integer quantidadeEstoque;

    @Column()
    private Integer quantidadeEstoqueEntrada;

    @Column(nullable = false, precision = 19 , scale = 2)
    private BigDecimal valorCompra;

    @Column(nullable = false, precision = 19 , scale = 2)
    private BigDecimal valorCompraEntrada;

    @Column(nullable = false, precision = 19 , scale = 2)
    private BigDecimal valorVenda;

    @Column(nullable = false, precision = 19 , scale = 2)
    private BigDecimal valorVendaEntrada;

    @ManyToOne
    private Marca marca;

}


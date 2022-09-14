package com.api.sistemachamados.entity;

import com.api.sistemachamados.dto.ProdutoDTO;
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
@Builder
@AllArgsConstructor
@Table(name = "produto")
@EqualsAndHashCode(callSuper = true)
public class Produto extends Auditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nomeProduto;

    @Column(nullable = false)
    private Integer quantidadeEstoque;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valorCompra;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valorVenda;

    @ManyToOne
    private Marca marca;
}

